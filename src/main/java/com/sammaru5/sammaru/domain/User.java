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
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String studentId;
    private String username;
    private String password;
    private String email;
    private Long point;
    private Integer generation;
    private Integer grade;

    @Enumerated(value = EnumType.STRING)
    private UserAuthority role;

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

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    /**
     * 해당 User의 Role이 어드민인지 판별하는 메서드
     * @return
     */
    public boolean isAdmin() {
        return this.getRole() == UserAuthority.ROLE_ADMIN;
    }

    public void modifyUserInfo(UserRequest userRequest, PasswordEncoder passwordEncoder) {
        this.username = userRequest.getUsername();
        this.email = userRequest.getEmail();
        this.password = passwordEncoder.encode(userRequest.getPassword());
        this.grade = userRequest.getGrade();
        this.studentId = userRequest.getStudentId();
    }

    public void modifyUsername(String username) {
        this.username = username;
    }

    public void modifyEmail(String email) {
        this.email = email;
    }

    public void modifyPassword(String password, PasswordEncoder encoder) {
        this.password = encoder.encode(password);
    }

    public void modifyStudentId(String studentId) {
        this.studentId = studentId;
    }
}