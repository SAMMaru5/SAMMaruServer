package com.sammaru5.sammaru.web.controller.auth;

import com.sammaru5.sammaru.config.jwt.JwtToken;
import com.sammaru5.sammaru.config.security.SecurityUtil;
import com.sammaru5.sammaru.service.user.*;
import com.sammaru5.sammaru.web.apiresult.ApiResult;
import com.sammaru5.sammaru.web.dto.JwtDto;
import com.sammaru5.sammaru.web.dto.UserDTO;
import com.sammaru5.sammaru.web.request.SignInRequest;
import com.sammaru5.sammaru.web.request.SignUpRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Duration;

@RestController
@RequiredArgsConstructor
@Api(tags = {"회원 인증 API"})
public class AuthController {

    private final UserRegisterService userRegisterService;
    private final UserLoginService userLoginService;
    private final UserReissueService userReissueService;
    private final UserTempPasswordService userTempPasswordService;
    private final UserLogoutService userLogoutService;

    @Value("${sammaru.cookie.domain}")
    private String cookieDomain;

    @Value("${sammaru.cookie.secure}")
    private boolean cookieSecure;

    @PostMapping("/auth/signup")
    @ApiOperation(value = "회원가입", notes = "사용자 회원가입", response = UserDTO.class)
    public ApiResult<?> userSignUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return ApiResult.OK(userRegisterService.signUpUser(signUpRequest));
    }

    @PostMapping("/auth/login")
    @ApiOperation(value = "로그인", notes = "엑세스 토큰과 리프레쉬 토큰 발급", response = JwtDto.class)
    public ResponseEntity<?> userSignIn(@Valid @RequestBody SignInRequest signInRequest) {
        JwtToken jwtToken = userLoginService.login(signInRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, createRefreshTokenCookie(jwtToken).toString())
                .body(ApiResult.OK(jwtToken.toDto()));
    }

    @PostMapping("/auth/reissue")
    @ApiOperation(value = "엑세스 토큰 재발급", notes = "만료된 엑세스 토큰과, 리프레쉬 토큰을 이용해 토큰 재 발급", response = JwtDto.class)
    public ResponseEntity<?> userReissue(@Valid @RequestBody TokenRequest tokenRequest,
                                         @CookieValue(name = "samrft") String refreshToken) {
        JwtToken jwtTokenRequest = JwtToken.ofTokens(tokenRequest.getAccessToken(), refreshToken);
        JwtToken jwtTokenResponse = userReissueService.reissue(jwtTokenRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, createRefreshTokenCookie(jwtTokenResponse).toString())
                .body(ApiResult.OK(jwtTokenResponse.toDto()));
    }

    @PostMapping("/auth/tempPassword")
    @ApiOperation(value = "임시 비밀번호 생성", notes = "계정에 등록되어 있는 메일로 임시 비밀번호 발송, 성공시 해당 유저의 id 반환", response = Long.class)
    public ApiResult<Long> sendTempPassword(@RequestParam String userEmail) {
        return ApiResult.OK(userTempPasswordService.sendTempPassword(userEmail));
    }

    @DeleteMapping("/auth/logout")
    @ApiOperation(value = "로그아웃", notes = "Refresh Token 삭제")
    public ResponseEntity<?> logout() {
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, removeRefreshTokenCookie().toString())
                .body(ApiResult.OK(userLogoutService.deleteRefreshToken(SecurityUtil.getCurrentUserId())));
    }

    private ResponseCookie createRefreshTokenCookie(JwtToken jwtToken) {
        return ResponseCookie.from("samrft", jwtToken.getRefreshToken())
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/auth/reissue")
                .maxAge(Duration.ofMillis(jwtToken.getRefreshTokenExpiresTime()))
                .sameSite("Strict")
                .domain(cookieDomain)
                .build();
    }

    private ResponseCookie removeRefreshTokenCookie() {
        return ResponseCookie.from("samrft", "")
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/auth/reissue")
                .maxAge(0)
                .sameSite("Strict")
                .domain(cookieDomain)
                .build();
    }

}
