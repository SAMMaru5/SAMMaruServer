package com.sammaru5.sammaru.security;

import com.sammaru5.sammaru.domain.entity.UserEntity;
import com.sammaru5.sammaru.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

//Spring Security에서 유저를 찾는 메소드 제공
@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String studentId) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByStudentId(studentId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with Student Id : " + studentId));
        return new UserDetail(userEntity);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Member not found with Id : " + id));
        return new UserDetail(userEntity);
    }

}
