package com.sammaru5.sammaru.service.user;

import com.sammaru5.sammaru.domain.UserAuthority;
import com.sammaru5.sammaru.domain.User;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.web.dto.UserDTO;
import com.sammaru5.sammaru.repository.UserRepository;
import com.sammaru5.sammaru.web.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service @RequiredArgsConstructor
public class UserRegisterService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO signUpUser(SignUpRequest signUpRequest) {
        if(userRepository.existsByStudentId(signUpRequest.getStudentId())) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_USER, signUpRequest.getStudentId());
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_EMAIL, signUpRequest.getEmail());
        }

        User user = signUpRequest.toEntityWithEncryptingPassword(passwordEncoder);
        return new UserDTO(userRepository.save(user));
    }
}
