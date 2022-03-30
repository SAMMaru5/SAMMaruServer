package com.sammaru5.sammaru.request;

import com.sammaru5.sammaru.domain.UserAuthority;
import lombok.Getter;

@Getter
public class RoleRequest {
    private UserAuthority role;
}
