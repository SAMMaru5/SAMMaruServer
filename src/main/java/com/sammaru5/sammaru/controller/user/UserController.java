package com.sammaru5.sammaru.controller.user;

import com.sammaru5.sammaru.apiresult.ApiResult;
import com.sammaru5.sammaru.request.RoleRequest;
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

    @PatchMapping("/users/{userId}/role")
    public ApiResult<?> roleChange(@RequestBody RoleRequest roleRequest, @PathVariable Long userId){
        return ApiResult.OK(userRoleService.changeRole(userId, roleRequest.getRole()));
    }

    @GetMapping("/user/info")
    public ApiResult<?> userInfo(Authentication authentication) {
        try{
            return ApiResult.OK(userSearchService.getUserFromToken(authentication));
        } catch(Exception e) {
            return ApiResult.ERROR(e, HttpStatus.UNAUTHORIZED);
        }
    }
}
