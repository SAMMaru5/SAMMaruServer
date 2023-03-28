package com.sammaru5.sammaru.web.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class VerificationCodeRequest {

    @NotBlank(message = "인증번호는 필수 입력입니다.")
    private String verificationCode;

}