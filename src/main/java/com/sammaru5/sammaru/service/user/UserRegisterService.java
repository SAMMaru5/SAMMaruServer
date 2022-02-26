package com.sammaru5.sammaru.service.user;

import com.sammaru5.sammaru.domain.entity.UserAuthority;
import com.sammaru5.sammaru.domain.entity.UserEntity;
import com.sammaru5.sammaru.domain.repository.UserRepository;
import com.sammaru5.sammaru.exception.AlreadyExistStudentIdException;
import com.sammaru5.sammaru.request.SignUpRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserRegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntity signUpUser(SignUpRequest signUpRequest) throws Exception {

        if(userRepository.existsByStudentId(signUpRequest.getStudentId())) {
            throw new AlreadyExistStudentIdException();
        }
        UserEntity userEntity = UserEntity.builder()
                .studentId(signUpRequest.getStudentId())
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .email(signUpRequest.getEmail())
                .point(Long.valueOf(0))
                .role(UserAuthority.ROLE_TEMP)
                .build();

        return userRepository.save(userEntity);
    }
}
