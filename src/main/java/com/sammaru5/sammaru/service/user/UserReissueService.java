package com.sammaru5.sammaru.service.user;

import com.sammaru5.sammaru.config.jwt.JwtToken;
import com.sammaru5.sammaru.config.jwt.TokenProvider;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.util.redis.RedisUtil;
import com.sammaru5.sammaru.web.controller.auth.TokenRequest;
import com.sammaru5.sammaru.web.dto.JwtDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserReissueService {
    private final TokenProvider tokenProvider;
    private final RedisUtil redisUtil;
    @Value("${app.jwtRefreshTokenValidTime}") //리프레쉬 토큰 유효시간
    private long REFRESH_TOKEN_EXPIRE_TIME;

    @Transactional
    public JwtDTO reissue(TokenRequest tokenRequest) {

        if (!tokenProvider.validateToken(tokenRequest.getAccessToken())) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        Authentication authentication = tokenProvider.getAuthentication(tokenRequest.getAccessToken());
        String realRefreshToken = redisUtil.getData("RT:" + authentication.getName());

        if (!realRefreshToken.equals(tokenRequest.getRefreshToken())) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        JwtToken tokenDto = tokenProvider.generateTokenDto(authentication);
        redisUtil.setDataExpire("RT:" + authentication.getName(), tokenDto.getRefreshToken(), REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);

        return tokenDto.toDto();
    }
}
