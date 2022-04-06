package com.sammaru5.sammaru.controller.user;

import com.sammaru5.sammaru.apiresult.ApiResult;
import com.sammaru5.sammaru.dto.UserDTO;
import com.sammaru5.sammaru.request.PointRequest;
import com.sammaru5.sammaru.request.RoleRequest;
import com.sammaru5.sammaru.request.UserRequest;
import com.sammaru5.sammaru.service.user.UserModifyService;
import com.sammaru5.sammaru.service.user.UserStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController @RequiredArgsConstructor
@Api(tags = {"사용자 API"})
public class UserController {
    private final UserStatusService userStatusService;
    private final UserModifyService userModifyService;

    @PatchMapping("/api/users/{userId}/role")
    @ApiOperation(value = "유저 권한 변경", notes = "userId에 해당하는 유저 권한 변경", response = UserDTO.class)
    public ApiResult<?> userRoleModify(@Valid  @RequestBody RoleRequest roleRequest, @PathVariable Long userId){
        return ApiResult.OK(userModifyService.modifyUserRole(userId, roleRequest.getRole()));
    }

    @PostMapping("/api/users/{userId}/point")
    @ApiOperation(value = "유저 포인트 추가", notes = "userId에 해당하는 유저 포인트 부여 (음수 가능)", response = UserDTO.class)
    public ApiResult<?> userPointAdd(@Valid @RequestBody PointRequest pointRequest, @PathVariable Long userId){
        try{
            return ApiResult.OK(userModifyService.addUserPoint(userId, pointRequest));
        } catch (Exception e){
            return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/user/info")
    @ApiOperation(value = "회원 정보", notes = "회원 정보 가져오기", response = UserDTO.class)
    public ApiResult<?> userDetails(Authentication authentication) {
        try{
            return ApiResult.OK(new UserDTO(userStatusService.getUser(authentication)));
        } catch(Exception e) {
            return ApiResult.ERROR(e, HttpStatus.UNAUTHORIZED);
        }
    }

    @PatchMapping("/api/user/info")
    @ApiOperation(value = "회원 정보 수정", notes = "회원 정보 수정하기", response = UserDTO.class)
    public ApiResult<?> userModify(Authentication authentication, @Valid @RequestBody UserRequest userRequest){
        try{
            return ApiResult.OK(userModifyService.modifyUser(authentication, userRequest));
        } catch(Exception e) {
            e.printStackTrace();
            return ApiResult.ERROR(e, HttpStatus.UNAUTHORIZED);
        }
    }
}
