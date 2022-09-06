package com.sammaru5.sammaru.service.user;

import com.sammaru5.sammaru.domain.User;
import com.sammaru5.sammaru.domain.UserAuthority;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.repository.UserRepository;
import com.sammaru5.sammaru.web.dto.UserDTO;
import com.sammaru5.sammaru.web.request.PointRequest;
import com.sammaru5.sammaru.web.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class UserModifyService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO modifyUser(Long userId, UserRequest userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, userId.toString()));

        if (!user.getEmail().equals(userRequest.getEmail()) && userRepository.existsByEmail(userRequest.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_EMAIL, userRequest.getEmail());
        }

        user.modifyUserInfo(userRequest, passwordEncoder);
        return new UserDTO(user);
    }

    public UserDTO modifyUserRole(Long userId, UserAuthority role) {
        User user = userRepository.findById(userId).get();
        user.setRole(role);
        return new UserDTO(userRepository.save(user));
    }

    public UserDTO addUserPoint(Long userId, PointRequest pointRequest) throws CustomException {
        User user = userRepository.getById(userId);
        if (user.getPoint() + pointRequest.getAddPoint() < 0) {
            throw new CustomException(ErrorCode.USER_POINT_CANT_NEGATIVE, userId.toString());
        }
        user.setPoint(user.getPoint() + pointRequest.getAddPoint());
        return new UserDTO(userRepository.save(user));
    }

    public UserDTO modifyUserGeneration(Long userId, Integer generation) {
        User user = userRepository.findById(userId).get();
        user.setGeneration(generation);
        return new UserDTO(user);
    }
}
