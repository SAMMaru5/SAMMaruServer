package com.sammaru5.sammaru.service.user;

import com.sammaru5.sammaru.web.dto.JwtDTO;
import com.sammaru5.sammaru.web.request.SignInRequest;
import com.sammaru5.sammaru.config.jwt.JwtTokenProvider;
import com.sammaru5.sammaru.config.security.UserDetail;
import com.sammaru5.sammaru.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service @RequiredArgsConstructor
public class UserLoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    public JwtDTO signInUser(SignInRequest signInRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getStudentId(),
                        signInRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtTokenProvider.generateToken(((UserDetail)authentication.getPrincipal()).getId());
        String refreshToken = jwtTokenProvider.generateRefreshToken();
        redisService.setValues(refreshToken, Long.toString(((UserDetail)authentication.getPrincipal()).getId()));

        return new JwtDTO(accessToken, refreshToken);
    }
}
