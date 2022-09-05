package com.sammaru5.sammaru.domain;

import com.sammaru5.sammaru.web.request.UserRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String studentId;
    private String username;
    private String password;
    private String email;
    private Long point;
    private Integer generation;

    @Enumerated(value = EnumType.STRING)
    private UserAuthority role;

    @Builder
    public User(String studentId, String username, String password, String email, Long point, Integer generation, UserAuthority role) {
        this.studentId = studentId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.point = point;
        this.generation = generation;
        this.role = role;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void setRole(UserAuthority role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public void setGeneration(Integer generation) {
        this.generation = generation;
    }

    public void modifyUserInfo(UserRequest userRequest, PasswordEncoder passwordEncoder) {
        this.username = userRequest.getUsername();
        this.email = userRequest.getEmail();
        this.password = passwordEncoder.encode(userRequest.getPassword());
    }
}
