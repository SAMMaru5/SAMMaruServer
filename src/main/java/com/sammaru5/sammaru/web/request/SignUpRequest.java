package com.sammaru5.sammaru.web.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class SignUpRequest {

    @NotBlank(message = "학번은 필수 입력입니다")
    @Size(min = 4, message = "학번은 자신의 학번 풀네임 입니다 ex) 2016XXXXXX")
    private String studentId;

    @NotBlank(message = "이름은 필수 입력입니다")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력입니다")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "비밀번호는 8~20자로 영문, 숫자, 특수문자를 적어도 하나 사용해야 됩니다")
    private String password;

    @NotBlank(message = "이메일은 필수 입력입니다")
    @Email(message = "이메일은 이메일 형식에 맞아야 됩니다")
    private String email;
}
