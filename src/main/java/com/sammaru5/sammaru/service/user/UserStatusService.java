package com.sammaru5.sammaru.service.user;

import com.sammaru5.sammaru.domain.UserEntity;
import com.sammaru5.sammaru.repository.UserRepository;
import com.sammaru5.sammaru.config.security.UserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@Service @RequiredArgsConstructor
public class UserStatusService {
    private final UserRepository userRepository;

    public UserEntity getUser(Authentication authentication) {
        UserDetail principal = (UserDetail) authentication.getPrincipal();
        Optional<UserEntity> findStudent = userRepository.findByStudentId(principal.getUsername());
        return findStudent.get();
    }
}
