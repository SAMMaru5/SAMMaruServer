package com.sammaru5.sammaru.controller.user;

import com.sammaru5.sammaru.apiresult.ApiResult;
import com.sammaru5.sammaru.request.RoleRequest;
import com.sammaru5.sammaru.service.user.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRoleService userRoleService;

    @PatchMapping("/{userId}/role")
    public ApiResult<?> roleChange(@RequestBody RoleRequest roleRequest, @PathVariable Long userId){
        return ApiResult.OK(userRoleService.changeRole(userId, roleRequest.getRole()));
    }
}
