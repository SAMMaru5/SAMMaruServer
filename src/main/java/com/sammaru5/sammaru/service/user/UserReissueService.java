package com.sammaru5.sammaru.service.user;

import com.sammaru5.sammaru.config.jwt.TokenDto;
import com.sammaru5.sammaru.config.jwt.TokenProvider;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.util.redis.RedisUtil;
import com.sammaru5.sammaru.web.controller.auth.TokenRequest;
import lombok.RequiredArgsConstructor;
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

    @Transactional
    public TokenDto reissue(TokenRequest tokenRequest) {

        if (!tokenProvider.validateToken(tokenRequest.getAccessToken())) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        Authentication authentication = tokenProvider.getAuthentication(tokenRequest.getAccessToken());
        String realRefreshToken = redisUtil.getData("RT:" + authentication.getName());

        if (!realRefreshToken.equals(tokenRequest.getRefreshToken())) {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);
        redisUtil.setDataExpire("RT:" + authentication.getName(), tokenDto.getRefreshToken(), tokenDto.getRefreshTokenExpiresTime(), TimeUnit.MILLISECONDS);

        return tokenDto;
    }
}
