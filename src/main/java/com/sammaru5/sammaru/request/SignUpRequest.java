package com.sammaru5.sammaru.request;

import lombok.Getter;

@Getter
public class SignUpRequest {
    private String studentId;
    private String username;
    private String password;
    private String email;
}
