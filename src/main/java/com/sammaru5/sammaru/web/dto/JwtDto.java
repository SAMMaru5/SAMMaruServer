package com.sammaru5.sammaru.web.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JwtDto {
    private final String tokenType = "Bearer";
    private String accessToken;
    private String accessTokenExpiresTime;
}
