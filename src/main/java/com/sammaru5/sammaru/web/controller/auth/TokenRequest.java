package com.sammaru5.sammaru.web.controller.auth;

import lombok.Data;

@Data
public class TokenRequest {
    private String accessToken;
    private String refreshToken;
}
