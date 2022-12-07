package com.sammaru5.sammaru.web.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JwtDTO {
    private final String tokenType = "Bearer";
    private String accessToken;
    private String refreshToken;
    private String accessTokenExpiresTime;
}
