package com.sammaru5.sammaru.config.jwt;

import com.sammaru5.sammaru.web.dto.JwtDTO;
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

    public JwtDTO toDto() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return JwtDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiresTime(sdf.format(accessTokenExpiresTime))
                .build();
    }
}