package com.sammaru5.sammaru.web.request;

import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;

@Getter
public class SignInRequest {
    @NotBlank(message = "학번을 입력해주세요")
    private String studentId;
    @NotBlank(message = "패스워드를 입력해주세요")
    private String password;

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(studentId, password);
    }
}
