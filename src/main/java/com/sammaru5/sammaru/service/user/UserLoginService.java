package com.sammaru5.sammaru.service.user;

import com.sammaru5.sammaru.config.jwt.TokenDto;
import com.sammaru5.sammaru.config.jwt.TokenProvider;
import com.sammaru5.sammaru.util.redis.RedisUtil;
import com.sammaru5.sammaru.web.request.SignInRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Transactional
@Service @RequiredArgsConstructor
public class UserLoginService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RedisUtil redisUtil;

    public TokenDto login(SignInRequest signInRequest) {

        UsernamePasswordAuthenticationToken authenticationToken = signInRequest.toAuthentication();

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        redisUtil.setDataExpire("RT:"+authentication.getName(), tokenDto.getRefreshToken(), tokenDto.getRefreshTokenExpiresTime(), TimeUnit.MILLISECONDS);

        return tokenDto;
    }
}
