package com.sammaru5.sammaru.web.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
public class UserInfoModifyRequestDto {
    @NotNull(message = "userId는 필수입니다.")
    private Long userId;
    @NotNull(message = "학번을 입력해주세요.")
    private String studentId;
    @NotNull(message = "이름을 입력해주세요.")
    private String username;
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;
    @NotNull(message = "기수를 입력해주세요.")
    private Integer generation;
}
