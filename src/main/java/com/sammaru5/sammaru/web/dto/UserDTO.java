package com.sammaru5.sammaru.web.dto;

import com.sammaru5.sammaru.domain.User;
import com.sammaru5.sammaru.domain.UserAuthority;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDTO {
    private Long userId;
    private String studentId;
    private String username;
    private String email;
    private Long point;
    private UserAuthority role;
    private Integer generation;
    private String nickname;

    public UserDTO(User user){
        this.userId = user.getId();
        this.studentId = user.getStudentId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.point = user.getPoint();
        this.role = user.getRole();
        this.generation = user.getGeneration();
        this.nickname = studentId.substring(2,4) + " " + user.getUsername();
    }
}
