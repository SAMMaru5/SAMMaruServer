package com.sammaru5.sammaru.web.controller.auth;

import com.sammaru5.sammaru.config.jwt.AccessTokenResponseDto;
import com.sammaru5.sammaru.config.jwt.TokenDto;
import com.sammaru5.sammaru.config.security.SecurityUtil;
import com.sammaru5.sammaru.service.user.*;
import com.sammaru5.sammaru.web.apiresult.ApiResult;
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

    @PostMapping("/auth/signup")
    @ApiOperation(value = "회원가입", notes = "사용자 회원가입", response = UserDTO.class)
    public ApiResult<?> userSignUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return ApiResult.OK(userRegisterService.signUpUser(signUpRequest));
    }

    @PostMapping("/auth/login")
    @ApiOperation(value = "로그인", notes = "엑세스 토큰과 리프레쉬 토큰 발급", response = AccessTokenResponseDto.class)
    public ResponseEntity<? extends ApiResult> userSignIn(@Valid @RequestBody SignInRequest signInRequest) {

        TokenDto tokenDto = userLoginService.login(signInRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, createAccessTokenCookie(tokenDto).toString())
                .header(HttpHeaders.SET_COOKIE, createRefreshTokenCookie(tokenDto).toString())
                .body(ApiResult.OK(new AccessTokenResponseDto(tokenDto)));
    }

    @PostMapping("/auth/reissue")
    @ApiOperation(value = "엑세스 토큰 재발급", notes = "만료된 엑세스 토큰과, 리프레쉬 토큰을 이용해 토큰 재 발급", response = AccessTokenResponseDto.class)
    public ResponseEntity<? extends ApiResult> userReissue(@CookieValue(name = "SammaruAccessToken") String accessToken, @CookieValue(name = "SammaruRefreshToken") String refreshToken) {
        TokenDto tokenDto = userReissueService.reissue(accessToken, refreshToken);

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, createAccessTokenCookie(tokenDto).toString())
                .header(HttpHeaders.SET_COOKIE, createRefreshTokenCookie(tokenDto).toString())
                .body(ApiResult.OK(new AccessTokenResponseDto(tokenDto)));
    }

    @PostMapping("/auth/tempPassword")
    @ApiOperation(value = "임시 비밀번호 생성", notes = "계정에 등록되어 있는 메일로 임시 비밀번호 발송, 성공시 해당 유저의 id 반환", response = Long.class)
    public ApiResult<Long> sendTempPassword(@RequestParam String userEmail) {
        return ApiResult.OK(userTempPasswordService.sendTempPassword(userEmail));
    }

    @DeleteMapping("/auth/logout")
    @ApiOperation(value = "로그아웃", notes = "Refresh Token 삭제 + 프론트단 Access, Refresh 쿠키 삭제")
    public ResponseEntity<? extends ApiResult<Boolean>> logout() {
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, createAccessTokenDeleteCookie().toString())
                .header(HttpHeaders.SET_COOKIE, createRefreshTokenDeleteCookie().toString())
                .body(ApiResult.OK(userLogoutService.deleteRefreshToken(SecurityUtil.getCurrentUserId())));
    }

    private ResponseCookie createRefreshTokenDeleteCookie() {
        return ResponseCookie.from("SammaruRefreshToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/auth/reissue")
                .maxAge(0)
                .sameSite("Strict")
                .domain(cookieDomain)
                .build();
    }

    private ResponseCookie createAccessTokenDeleteCookie() {
        return ResponseCookie.from("SammaruAccessToken", "")
                .httpOnly(false)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .domain(cookieDomain)
                .build();
    }

    private ResponseCookie createRefreshTokenCookie(TokenDto tokenDto) {
        return ResponseCookie.from("SammaruRefreshToken", tokenDto.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/auth/reissue")
                .maxAge(Duration.ofMillis(tokenDto.getRefreshTokenExpiresTime()))
                .sameSite("Strict")
                .domain(cookieDomain)
                .build();
    }

    private ResponseCookie createAccessTokenCookie(TokenDto tokenDto) {
        return ResponseCookie.from("SammaruAccessToken", tokenDto.getAccessToken())
                .httpOnly(false)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofMillis(1000 * 60 * 30L))
                .sameSite("Strict")
                .domain(cookieDomain)
                .build();
    }
}
