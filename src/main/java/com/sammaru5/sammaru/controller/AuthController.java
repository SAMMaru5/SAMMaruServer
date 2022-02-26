package com.sammaru5.sammaru.controller;


import com.sammaru5.sammaru.apiresult.ApiResult;
import com.sammaru5.sammaru.request.SignUpRequest;
import com.sammaru5.sammaru.service.user.UserRegisterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final UserRegisterService userRegisterService;

    @PostMapping("/signup")
    public ApiResult<?> userSignUp(@Valid @RequestBody SignUpRequest signUpRequest){
        try{
            return ApiResult.OK(userRegisterService.signUpUser(signUpRequest));
        } catch(Exception e) {
            return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
        }
    }
}
