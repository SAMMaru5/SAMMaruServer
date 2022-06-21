package com.sammaru5.sammaru.web.controller;

import com.sammaru5.sammaru.domain.UserEntity;
import com.sammaru5.sammaru.util.AuthUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public UserEntity test(@AuthUser UserEntity user) {
        return user;
    }
}
