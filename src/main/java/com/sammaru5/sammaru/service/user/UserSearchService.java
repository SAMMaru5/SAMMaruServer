package com.sammaru5.sammaru.service.user;

import com.sammaru5.sammaru.domain.UserAuthority;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.repository.UserRepository;
import com.sammaru5.sammaru.web.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserSearchService {
    private final UserRepository userRepository;

    public UserDTO findOne(Long userId) {
        return new UserDTO(userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, userId.toString())));
    }

    public List<UserDTO> findAllUsers(){
        return userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public List<UserDTO> findUsersByRole(UserAuthority role) {
        return userRepository.findByRole(role).stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public List<UserDTO> findUsersByGeneration(Integer generationNum) {
        return userRepository.findByGeneration(generationNum).stream().map(UserDTO::new).collect(Collectors.toList());
    }
}
