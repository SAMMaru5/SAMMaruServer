package com.sammaru5.sammaru.web.request;

import com.sammaru5.sammaru.domain.UserAuthority;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class RoleRequest {
    @NotBlank(message = "부여할 역할을 입력해주세요")
    private UserAuthority role;
}
