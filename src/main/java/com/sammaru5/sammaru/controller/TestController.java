package com.sammaru5.sammaru.controller;

import com.sammaru5.sammaru.domain.UserEntity;
import com.sammaru5.sammaru.security.AuthUser;
import com.sammaru5.sammaru.security.UserDetail;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public UserEntity test(@AuthUser UserEntity user) {
        return user;
    }
}
