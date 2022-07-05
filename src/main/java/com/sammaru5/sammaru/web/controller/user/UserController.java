package com.sammaru5.sammaru.web.controller.user;

import com.sammaru5.sammaru.domain.User;
import com.sammaru5.sammaru.domain.UserAuthority;
import com.sammaru5.sammaru.service.user.UserModifyService;
import com.sammaru5.sammaru.service.user.UserSearchService;
import com.sammaru5.sammaru.util.AuthUser;
import com.sammaru5.sammaru.util.OverAdminRole;
import com.sammaru5.sammaru.util.OverMemberRole;
import com.sammaru5.sammaru.web.apiresult.ApiResult;
import com.sammaru5.sammaru.web.dto.UserDTO;
import com.sammaru5.sammaru.web.request.PointRequest;
import com.sammaru5.sammaru.web.request.RoleRequest;
import com.sammaru5.sammaru.web.request.UserRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Api(tags = {"사용자 API"})
@RestController
public class UserController {

    private final UserModifyService userModifyService;
    private final UserSearchService userSearchService;

    @PatchMapping("/api/users/{userId}/role")
    @ApiOperation(value = "유저 권한 변경", notes = "userId에 해당하는 유저 권한 변경", response = UserDTO.class)
    @OverAdminRole
    public ApiResult<UserDTO> userRoleModify(@Valid  @RequestBody RoleRequest roleRequest, @PathVariable Long userId){
        return ApiResult.OK(userModifyService.modifyUserRole(userId, roleRequest.getRole()));
    }

    @PostMapping("/api/users/{userId}/point")
    @ApiOperation(value = "유저 포인트 추가", notes = "userId에 해당하는 유저 포인트 부여 (음수 가능)", response = UserDTO.class)
    @OverAdminRole
    public ApiResult<UserDTO> userPointAdd(@Valid @RequestBody PointRequest pointRequest, @PathVariable Long userId){
        return ApiResult.OK(userModifyService.addUserPoint(userId, pointRequest));
    }

    @GetMapping("/api/user/info")
    @ApiOperation(value = "자기 자신 회원 정보", notes = "자기 자신의 회원 정보 가져오기", response = UserDTO.class)
    public ApiResult<UserDTO> loginUserSelfDetail(@AuthUser User user) {
        return ApiResult.OK(new UserDTO(user));
    }

    @ApiOperation(value = "특정 회원 조회", notes = "userId에 대한 특정 회원 정보를 반환합니다.")
    @OverAdminRole
    @GetMapping("/api/user/info/{userId}")
    public ApiResult<UserDTO> userDetail(@PathVariable Long userId) {
        return ApiResult.OK(userSearchService.findOne(userId));
    }

    @GetMapping("/api/users/info")
    @ApiOperation(value = "전체 회원 정보", notes = "전체 회원 정보 가져오기", responseContainer = "List", response = UserDTO.class)
    @OverAdminRole
    public ApiResult<List<UserDTO>> userList() {
        return ApiResult.OK(userSearchService.findAllUsers());
    }

    @GetMapping("/api/users")
    @ApiOperation(value = "해당 역할의 회원 정보", notes = "쿼리의 역할에 맞는 회원 정보들 가져오기", responseContainer = "List", response = UserDTO.class)
    @OverAdminRole
    public ApiResult<List<UserDTO>> UserListByRole(@RequestParam UserAuthority role) {
        return ApiResult.OK(userSearchService.findUsersByRole(role));
    }

    @PatchMapping("/api/user/info")
    @ApiOperation(value = "회원 정보 수정", notes = "회원 정보 수정하기", response = UserDTO.class)
    public ApiResult<UserDTO> userModify(Authentication authentication, @Valid @RequestBody UserRequest userRequest){
        return ApiResult.OK(userModifyService.modifyUser(authentication, userRequest));
    }

    @GetMapping("/api/users/gen/{generationNum}")
    @ApiOperation(value = "기수에 대한 회원 정보 조회", notes = "generationNum으로 넘어온 기수에 대한 회원들에 대한 정보를 조회합니다.", response = UserDTO.class)
    @OverMemberRole
    public ApiResult<List<UserDTO>> userListOfGeneration(@PathVariable Integer generationNum) {
        return ApiResult.OK(userSearchService.findUsersByGeneration(generationNum));
    }
}
