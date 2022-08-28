package com.sammaru5.sammaru.config.jwt;

import com.sammaru5.sammaru.domain.UserAuthority;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";

    @Value("${app.jwtTokenValidTime}") //설정파일의 토큰 유효시간
    private static long ACCESS_TOKEN_EXPIRE_TIME;

    @Value("${app.jwtRefreshTokenValidTime}") //리프레쉬 토큰 유효시간
    private static long REFRESH_TOKEN_EXPIRE_TIME;

    private final Key key;

    public TokenProvider(@Value("${app.jwtSecretKey}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto generateTokenDto(Authentication authentication) {

        // 1. 권한 가져옵니다.
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        if(authorities.contains(UserAuthority.ROLE_TEMP.toString())) throw new CustomException(ErrorCode.UNAUTHORIZED_USER_ACCESS);

        long now = (new Date()).getTime();

        Date accessTokenExpiresAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

        // 2. Access Token을 생성합니다.
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(accessTokenExpiresAt)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        // 3. RefreshToken을 생성합니다.
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return TokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresTime(accessTokenExpiresAt)
                .refreshTokenExpiresTime(REFRESH_TOKEN_EXPIRE_TIME)
                .refreshToken(refreshToken)
                .build();
    }

    public Authentication getAuthentication(String accessToken) {
        // 1. Access Token 을 복호화합니다.
        Claims claims = parseClaims(accessToken);

        // 만료된 accessToken 일 때
        if (claims.getExpiration().before(new Date())) {
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        }

        // 권한 정보가 없는 토큰일 때
        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new CustomException(ErrorCode.TOKEN_WITHOUT_AUTHORITY);
        }

        // 2. Claim에서 권한 정보를 가져옵니다.
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // 3. UserDetails 객체를 만들어서 Authentication을 반환합니다.
        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원하지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
