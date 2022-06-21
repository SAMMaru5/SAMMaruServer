package com.sammaru5.sammaru.config.security;

import com.sammaru5.sammaru.domain.UserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
public class UserDetail implements UserDetails {

//    private Long id;
//    private String studentId;
//    private String username;
//    private String password;
//    private String email;
//    private Long point;
//    private UserAuthority userAuthority;
    private UserEntity userEntity;

    public UserDetail (UserEntity userEntity) {
        this.userEntity = userEntity;
//        this.id = userEntity.getId();
//        this.studentId = userEntity.getStudentId();
//        this.username = userEntity.getUsername();
//        this.password = userEntity.getPassword();
//        this.email = userEntity.getEmail();
//        this.point = userEntity.getPoint();
//        this.userAuthority = userEntity.getRole();
    }

    public Long getId() {return userEntity.getId();}

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        authorities.add(new SimpleGrantedAuthority(this.userEntity.getRole().name()));

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        UserDetail that = (UserDetail) o;
        return Objects.equals(this.userEntity.getId(), that.userEntity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.userEntity.getId());
    }
}
