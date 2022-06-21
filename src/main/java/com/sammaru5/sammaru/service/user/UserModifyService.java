package com.sammaru5.sammaru.service.user;

import com.sammaru5.sammaru.domain.UserAuthority;
import com.sammaru5.sammaru.domain.UserEntity;
import com.sammaru5.sammaru.web.dto.UserDTO;
import com.sammaru5.sammaru.repository.UserRepository;
import com.sammaru5.sammaru.web.request.PointRequest;
import com.sammaru5.sammaru.web.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service @RequiredArgsConstructor
public class UserModifyService {
    private final UserRepository userRepository;
    private final UserStatusService userStatusService;
    private final PasswordEncoder passwordEncoder;

    public UserDTO modifyUser(Authentication authentication, UserRequest userRequest) {
        UserEntity userEntity = userStatusService.getUser(authentication);
        if (userRequest.getUsername() != null) {
            userEntity.setUsername(userRequest.getUsername());
        }
        if (userRequest.getEmail() != null) {
            userEntity.setEmail(userRequest.getEmail());
        }
        if (userRequest.getPassword() != null) {
            userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }
        return new UserDTO(userRepository.save(userEntity));
    }

    public UserDTO modifyUserRole(Long userId, UserAuthority role){
        UserEntity userEntity = userRepository.findById(userId).get();
        userEntity.setRole(role);
        return new UserDTO(userRepository.save(userEntity));
    }

    public UserDTO addUserPoint(Long userId, PointRequest pointRequest) throws IllegalArgumentException {
        UserEntity userEntity = userRepository.getById(userId);
        if(userEntity.getPoint() + pointRequest.getAddPoint() < 0){
            throw new IllegalArgumentException("사용자의 포인트는 음수가 될 수 없습니다!");
        }
        userEntity.setPoint(userEntity.getPoint() + pointRequest.getAddPoint());
        return new UserDTO(userRepository.save(userEntity));
    }
}
