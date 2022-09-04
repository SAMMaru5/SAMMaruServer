package com.sammaru5.sammaru.config.security;

import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtil {

    // Security Context에 User 정보가 저장되는 시점
    // Request가 들어올 때 JwtFilter의 doFilter 에서 저장
    public static Long getCurrentUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication.getName() == null) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        return Long.parseLong(authentication.getName());
    }
}
