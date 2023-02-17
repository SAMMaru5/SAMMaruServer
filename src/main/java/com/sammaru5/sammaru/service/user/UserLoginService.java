package com.sammaru5.sammaru.service.user;

import com.sammaru5.sammaru.config.jwt.JwtToken;
import com.sammaru5.sammaru.config.jwt.TokenProvider;
import com.sammaru5.sammaru.util.redis.RedisUtil;
import com.sammaru5.sammaru.web.request.SignInRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserLoginService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RedisUtil redisUtil;
    @Value("${app.jwtRefreshTokenValidTime}") //리프레쉬 토큰 유효시간
    private long REFRESH_TOKEN_EXPIRE_TIME;

    @Transactional
    public JwtToken login(SignInRequest signInRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = signInRequest.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        JwtToken jwtToken = tokenProvider.generateTokenDto(authentication);

        redisUtil.deleteData("RT:" + authentication.getName());
        redisUtil.setDataExpire("RT:" + authentication.getName(), jwtToken.getRefreshToken(), REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);

        return jwtToken;
    }
}
