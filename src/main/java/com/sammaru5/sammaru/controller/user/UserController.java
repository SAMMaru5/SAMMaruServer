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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRoleService userRoleService;
    private final UserSearchService userSearchService;
    private final UserModifyService userModifyService;
    private final UserPointService userPointService;

    @PatchMapping("/users/{userId}/role")
    public ApiResult<?> roleChange(@RequestBody RoleRequest roleRequest, @PathVariable Long userId){
        return ApiResult.OK(userRoleService.changeRole(userId, roleRequest.getRole()));
    }

    @PostMapping("/users/{userId}/point")
    public ApiResult<?> pointAdd(@RequestBody PointRequest pointRequest, @PathVariable Long userId){
        try{
            return ApiResult.OK(userPointService.addPoint(userId, pointRequest));
        } catch (Exception e){
            return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/info")
    public ApiResult<?> userInfo(Authentication authentication) {
        try{
            return ApiResult.OK(new UserDTO(userSearchService.getUserFromToken(authentication)));
        } catch(Exception e) {
            return ApiResult.ERROR(e, HttpStatus.UNAUTHORIZED);
        }
    }

    @PatchMapping("/user/info")
    public ApiResult<?> userInfoModify(Authentication authentication, @RequestBody UserRequest userRequest){
        try{
            return ApiResult.OK(userModifyService.modifyUser(authentication, userRequest));
        } catch(Exception e) {
            e.printStackTrace();
            return ApiResult.ERROR(e, HttpStatus.UNAUTHORIZED);
        }
    }
}
