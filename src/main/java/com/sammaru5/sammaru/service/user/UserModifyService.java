package com.sammaru5.sammaru.service.user;

import com.sammaru5.sammaru.domain.User;
import com.sammaru5.sammaru.domain.UserAuthority;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.repository.UserRepository;
import com.sammaru5.sammaru.web.dto.UserDTO;
import com.sammaru5.sammaru.web.request.PointRequest;
import com.sammaru5.sammaru.web.request.UserModifyRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserModifyService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO modifyUser(Long userId, UserModifyRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, userId.toString()));


        if (isNotProperStudentId(requestDto.getStudentId())) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_USER, requestDto.getStudentId());
        }
        user.modifyStudentId(requestDto.getStudentId());

        if (isNotProperEmail(requestDto.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_EMAIL, requestDto.getEmail());
        }
        user.modifyEmail(requestDto.getEmail());

        if (isNotProperUsername(requestDto.getUsername())) {
            throw new CustomException(ErrorCode.NULL_POINTER_EXCEPTION);
        }
        user.modifyUsername(requestDto.getUsername());

        if (isNotProperPassword(requestDto.getPassword())) {
            throw new CustomException(ErrorCode.INAPPROPRIATE_PASSWORD);
        }

        user.modifyPassword(requestDto.getPassword(), passwordEncoder);

        return UserDTO.from(user);
    }

    @Transactional
    public UserDTO modifyUserRole(Long userId, UserAuthority role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, userId.toString()));
        user.setRole(role);
        return UserDTO.from(user);
    }

    @Transactional
    public UserDTO addUserPoint(Long userId, PointRequest pointRequest) throws CustomException {
        User user = userRepository.getById(userId);
        if (user.getPoint() + pointRequest.getAddPoint() < 0) {
            throw new CustomException(ErrorCode.USER_POINT_CANT_NEGATIVE, userId.toString());
        }
        user.setPoint(user.getPoint() + pointRequest.getAddPoint());
        return UserDTO.from(user);
    }

    @Transactional
    public UserDTO modifyUserGeneration(Long userId, Integer generation) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, userId.toString()));
        user.setGeneration(generation);
        return UserDTO.from(user);
    }

    private boolean isNotProperStudentId(String studentId) {
        return studentId == null ||
                studentId.length() != 10 ||
                !studentId.startsWith("20") ||
                userRepository.existsByStudentId(studentId);
    }

    public boolean isNotProperUsername(String username) {
        return username == null;
    }

    public boolean isNotProperEmail(String email) {
        return email == null ||
                !email.contains("@") ||
                userRepository.existsByEmail(email);
    }

    public boolean isNotProperPassword(String password) {
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";
        return password == null || !Pattern.matches(passwordPattern, password);
    }
}
