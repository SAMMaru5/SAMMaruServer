package com.sammaru5.sammaru.controller.auth;


import com.sammaru5.sammaru.apiresult.ApiResult;
import com.sammaru5.sammaru.request.SignInRequest;
import com.sammaru5.sammaru.request.SignUpRequest;
import com.sammaru5.sammaru.service.user.UserLoginService;
import com.sammaru5.sammaru.service.user.UserRegisterService;
import com.sammaru5.sammaru.service.user.UserReissueService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final UserRegisterService userRegisterService;
    private final UserLoginService userLoginService;
    private final UserReissueService userReissueService;

    @PostMapping("/signup")
    public ApiResult<?> userSignUp(@Valid @RequestBody SignUpRequest signUpRequest){
        try{
            return ApiResult.OK(userRegisterService.signUpUser(signUpRequest));
        } catch(Exception e) {
            return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signin")
    public ApiResult<?> userSignIn(@Valid @RequestBody SignInRequest signInRequest) {
        return ApiResult.OK(userLoginService.signInUser(signInRequest));
    }

    @PostMapping("/reissue")
    public ApiResult<?> userReissue(HttpServletRequest request) {
        try{
            return ApiResult.OK(userReissueService.reissueUser(request));
        } catch (Exception e) {
            return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
        }
    }
}
