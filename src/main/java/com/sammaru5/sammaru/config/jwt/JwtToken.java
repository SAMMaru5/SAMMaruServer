package com.sammaru5.sammaru.config.jwt;

import com.sammaru5.sammaru.web.dto.JwtDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class JwtToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Date accessTokenExpiresTime;
    private long refreshTokenExpiresTime;

    public static JwtToken ofTokens(String accessToken, String refreshToken) {
        return JwtToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public JwtDto toDto() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return JwtDto.builder()
                .accessToken(accessToken)
                .accessTokenExpiresTime(sdf.format(accessTokenExpiresTime))
                .build();
    }
}