package com.sammaru5.sammaru.web.dto;

import com.sammaru5.sammaru.domain.UserAuthority;
import com.sammaru5.sammaru.domain.User;
import lombok.Getter;
import lombok.Setter;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
public class UserDTO {
    private String studentId;
    private String username;
    private String email;
    private Long point;
    private UserAuthority role;
    private Integer generation;
    private String nickname;

    public UserDTO(User user){
        copyProperties(user, this);
        this.nickname = studentId.substring(2,4) + " " + user.getUsername();
    }
}
