package com.sammaru5.sammaru.web.request;

import lombok.Getter;

@Getter
public class UserModifyRequestDto {

    private String username;
    private String password;
    private String email;
    private String studentId;

}
