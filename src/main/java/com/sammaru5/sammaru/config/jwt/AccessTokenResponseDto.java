package com.sammaru5.sammaru.config.jwt;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccessTokenResponseDto {
    private String accessToken;
    private Date expiresAt;

    public AccessTokenResponseDto(TokenDto tokenDto) {
        this.accessToken = tokenDto.getAccessToken();
        this.expiresAt = tokenDto.getAccessTokenExpiresTime();
    }
}
