package com.sammaru5.sammaru.dto;

import com.sammaru5.sammaru.domain.UserAuthority;
import com.sammaru5.sammaru.domain.UserEntity;
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

    public UserDTO(UserEntity userEntity){
        copyProperties(userEntity, this);
        this.nickname = studentId.substring(2,4) + " " + userEntity.getUsername();
    }
}
