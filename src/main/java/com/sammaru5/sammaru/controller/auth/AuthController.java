package com.sammaru5.sammaru.controller.auth;

import com.sammaru5.sammaru.apiresult.ApiResult;
import com.sammaru5.sammaru.dto.JwtDTO;
import com.sammaru5.sammaru.dto.UserDTO;
import com.sammaru5.sammaru.request.SignInRequest;
import com.sammaru5.sammaru.request.SignUpRequest;
import com.sammaru5.sammaru.service.user.UserLoginService;
import com.sammaru5.sammaru.service.user.UserRegisterService;
import com.sammaru5.sammaru.service.user.UserReissueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController @RequiredArgsConstructor
@Api(tags = {"회원 인증 API"})
public class AuthController {

    private final UserRegisterService userRegisterService;
    private final UserLoginService userLoginService;
    private final UserReissueService userReissueService;

    @PostMapping("/auth/signup")
    @ApiOperation(value = "회원가입", notes = "사용자 회원가입", response = UserDTO.class)
    public ApiResult<?> userSignUp(@Valid @RequestBody SignUpRequest signUpRequest){
        try{
            return ApiResult.OK(userRegisterService.signUpUser(signUpRequest));
        } catch(Exception e) {
            return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/auth/signin")
    @ApiOperation(value = "로그인", notes = "엑세스 토큰과 리프레쉬 토큰 발급", response = JwtDTO.class)
    public ApiResult<JwtDTO> userSignIn(@Valid @RequestBody SignInRequest signInRequest) {
        return ApiResult.OK(userLoginService.signInUser(signInRequest));
    }

    @PostMapping("/auth/reissue")
    @ApiOperation(value = "엑세스 토큰 재발급", notes = "만료된 엑세스 토큰과, 리프레쉬 토큰을 이용해 토큰 재 발급", response = JwtDTO.class)
    public ApiResult<?> userReissue(HttpServletRequest request) {
        try{
            return ApiResult.OK(userReissueService.reissueUser(request));
        } catch (Exception e) {
            return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
        }
    }
}
