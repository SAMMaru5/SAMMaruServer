package com.sammaru5.sammaru.web.controller.user;

import com.sammaru5.sammaru.config.security.SecurityUtil;
import com.sammaru5.sammaru.domain.UserAuthority;
import com.sammaru5.sammaru.service.user.UserModifyService;
import com.sammaru5.sammaru.service.user.UserRemoveService;
import com.sammaru5.sammaru.service.user.UserSearchService;
import com.sammaru5.sammaru.util.OverAdminRole;
import com.sammaru5.sammaru.util.OverMemberRole;
import com.sammaru5.sammaru.web.apiresult.ApiResult;
import com.sammaru5.sammaru.web.dto.UserDTO;
import com.sammaru5.sammaru.web.request.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RequiredArgsConstructor
@Api(tags = {"사용자 API"})
@RestController
public class UserController {

    private final UserModifyService userModifyService;
    private final UserSearchService userSearchService;
    private final UserRemoveService userRemoveService;

    @ApiOperation(value = "유저 권한 변경", notes = "userId에 해당하는 유저 권한 변경")
    @OverAdminRole
    @PatchMapping("/api/users/{userId}/role")
    public ApiResult<UserDTO> userRoleModify(@Valid @RequestBody RoleRequest roleRequest, @PathVariable Long userId) {
        return ApiResult.OK(userModifyService.modifyUserRole(userId, roleRequest.getRole()));
    }

    @ApiOperation(value = "유저 기수 설정", notes = "userId에 해당하는 유저 기수 변경")
    @PatchMapping("/api/users/{userId}/generation")
    @OverAdminRole
    public ApiResult<UserDTO> userGenerationModify(@Valid @RequestBody GenerationRequest generationRequest, @PathVariable Long userId) {
        return ApiResult.OK(userModifyService.modifyUserGeneration(userId, generationRequest.getGeneration()));
    }

    @ApiOperation(value = "유저 포인트 추가", notes = "userId에 해당하는 유저 포인트 부여 (음수 가능)")
    @OverAdminRole
    @PostMapping("/api/users/{userId}/point")
    public ApiResult<UserDTO> userPointAdd(@Valid @RequestBody PointRequest pointRequest, @PathVariable Long userId) {
        return ApiResult.OK(userModifyService.addUserPoint(userId, pointRequest));
    }

    @ApiOperation(value = "자기 자신 회원 정보", notes = "자기 자신의 회원 정보 가져오기, 토큰만 보내면 됩니다.")
    @GetMapping("/no-permit/api/user/info")
    public ApiResult<UserDTO> loginUserSelfDetail() {
        return ApiResult.OK(userSearchService.findOne(SecurityUtil.getCurrentUserId()));
    }

    @ApiOperation(value = "특정 회원 조회", notes = "userId에 대한 특정 회원 정보를 반환합니다.")
    @OverAdminRole
    @GetMapping("/api/user/info/{userId}")
    public ApiResult<UserDTO> userDetail(@PathVariable Long userId) {
        return ApiResult.OK(userSearchService.findOne(userId));
    }

    @ApiOperation(value = "전체 회원 정보", notes = "전체 회원 정보 가져오기", responseContainer = "List")
    @OverAdminRole
    @GetMapping("/api/users/info")
    public ApiResult<List<UserDTO>> userList() {
        return ApiResult.OK(userSearchService.findAllUsers());
    }

    @ApiOperation(value = "해당 역할의 회원 정보", notes = "쿼리의 역할에 맞는 회원 정보들 가져오기")
    @OverAdminRole
    @GetMapping("/api/users")
    public ApiResult<List<UserDTO>> UserListByRole(@RequestParam UserAuthority role) {
        return ApiResult.OK(userSearchService.findUsersByRole(role));
    }

    //deprecated
    @OverAdminRole
    @PatchMapping("/api/user/info")
    public ApiResult<UserDTO> userModify(@RequestBody UserModifyRequestDto requestDto) {
        return ApiResult.OK(userModifyService.modifyUser(SecurityUtil.getCurrentUserId(), requestDto));
    }

    @ApiOperation(value = "회원 정보 수정 v2", notes = "studentId, username, email, generation 회원 정보 수정하기")
    @OverAdminRole
    @PatchMapping("/api/v2/user/info")
    public ApiResult<UserDTO> userModifyV2(@NotBlank @RequestBody UserInfoModifyRequestDto requestDto) {
        return ApiResult.OK(userModifyService.modifyUserV2(requestDto));
    }

    @ApiOperation(value = "비밀번호 변경", notes = "비밀번호 변경하기")
    @OverMemberRole
    @PutMapping("/api/user/password")
    public ApiResult<?> userChangePassword(@NotBlank @RequestBody String password) {
        return ApiResult.OK(userModifyService.changePassword(SecurityUtil.getCurrentUserId(), password));
    }

    @GetMapping("/api/users/gen/{generationNum}")
    @ApiOperation(value = "기수에 대한 회원 정보 조회", notes = "generationNum으로 넘어온 기수에 대한 회원들에 대한 정보를 조회합니다.")
    @OverMemberRole
    public ApiResult<List<UserDTO>> userListOfGeneration(@PathVariable Integer generationNum) {
        return ApiResult.OK(userSearchService.findUsersByGeneration(generationNum));
    }

    @GetMapping("/api/users/detail")
    @ApiOperation(value = "username으로 회원 정보 조회", notes = "관리자 권한이 username으로 해당 회원의 정보를 조회합니다.")
    @OverAdminRole
    public ApiResult<List<UserDTO>> userDetailByUsername(@RequestParam String username) {
        return ApiResult.OK(userSearchService.findByUsername(username));
    }

    @DeleteMapping("/api/users/{userId}")
    @ApiOperation(value = "userId로 회원 추방", notes = "관리자 권한이 userId로 해당 회원을 추방합니다.")
    @OverAdminRole
    public ApiResult<?> userBanish(@Valid @PathVariable Long userId) {
        return ApiResult.OK(userRemoveService.banishUser(userId));
    }
}
