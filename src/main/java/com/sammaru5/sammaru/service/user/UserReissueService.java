package com.sammaru5.sammaru.service.user;

import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.web.dto.JwtDTO;
import com.sammaru5.sammaru.config.jwt.TokenProvider;
import com.sammaru5.sammaru.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Transactional
@Service @RequiredArgsConstructor
public class UserReissueService {
    private final TokenProvider jwtTokenProvider;
    private final RedisService redisService;

    public JwtDTO reissueUser(HttpServletRequest request) throws CustomException {

        String accessToken = jwtTokenProvider.getTokenFromRequest(request);
        String refreshToken = jwtTokenProvider.getRefreshTokenFromRequest(request);
        String newAccessToken;

        //리프레쉬 토큰 시간이 유효할때
        if(StringUtils.hasText(refreshToken) && jwtTokenProvider.validateToken(refreshToken)) {

            //리프레쉬 토큰 저장소 확인
            if((redisService.getValues(refreshToken) != null) && (redisService.getValues(refreshToken).equals(Long.toString(jwtTokenProvider.getIdFromToken(accessToken))))) {
                newAccessToken = jwtTokenProvider.generateToken(jwtTokenProvider.getIdFromToken(accessToken));
            } else {
                throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
            }
        } else {
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        return new JwtDTO(newAccessToken, refreshToken);
    }
}
