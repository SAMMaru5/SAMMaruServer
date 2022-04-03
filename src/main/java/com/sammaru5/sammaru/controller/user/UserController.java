package com.sammaru5.sammaru.controller.user;

import com.sammaru5.sammaru.apiresult.ApiResult;
import com.sammaru5.sammaru.dto.UserDTO;
import com.sammaru5.sammaru.request.PointRequest;
import com.sammaru5.sammaru.request.RoleRequest;
import com.sammaru5.sammaru.request.UserRequest;
import com.sammaru5.sammaru.service.user.UserModifyService;
import com.sammaru5.sammaru.service.user.UserPointService;
import com.sammaru5.sammaru.service.user.UserRoleService;
import com.sammaru5.sammaru.service.user.UserSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController @RequiredArgsConstructor
@Api(tags = {"사용자 API"})
public class UserController {

    private final UserRoleService userRoleService;
    private final UserSearchService userSearchService;
    private final UserModifyService userModifyService;
    private final UserPointService userPointService;

    @PatchMapping("/api/users/{userId}/role")
    @ApiOperation(value = "유저 권한 변경", notes = "userId에 해당하는 유저 권한 변경", response = UserDTO.class)
    public ApiResult<?> roleChange(@RequestBody RoleRequest roleRequest, @PathVariable Long userId){
        return ApiResult.OK(userRoleService.changeRole(userId, roleRequest.getRole()));
    }

    @PostMapping("/api/users/{userId}/point")
    @ApiOperation(value = "유저 포인트 추가", notes = "userId에 해당하는 유저 포인트 부여 (음수 가능)", response = UserDTO.class)
    public ApiResult<?> pointAdd(@RequestBody PointRequest pointRequest, @PathVariable Long userId){
        try{
            return ApiResult.OK(userPointService.addPoint(userId, pointRequest));
        } catch (Exception e){
            return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/user/info")
    @ApiOperation(value = "회원 정보", notes = "회원 정보 가져오기", response = UserDTO.class)
    public ApiResult<?> userInfo(Authentication authentication) {
        try{
            return ApiResult.OK(new UserDTO(userSearchService.getUserFromToken(authentication)));
        } catch(Exception e) {
            return ApiResult.ERROR(e, HttpStatus.UNAUTHORIZED);
        }
    }

    @PatchMapping("/api/user/info")
    @ApiOperation(value = "회원 정보 수정", notes = "회원 정보 수정하기", response = UserDTO.class)
    public ApiResult<?> userInfoModify(Authentication authentication, @RequestBody UserRequest userRequest){
        try{
            return ApiResult.OK(userModifyService.modifyUser(authentication, userRequest));
        } catch(Exception e) {
            e.printStackTrace();
            return ApiResult.ERROR(e, HttpStatus.UNAUTHORIZED);
        }
    }
}
