package com.sammaru5.sammaru.web.dto;

import lombok.Getter;

@Getter
public class JwtDTO {
    private String tokenType = "Bearer";
    private String accessToken;
    private String refreshToken;

    public JwtDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
