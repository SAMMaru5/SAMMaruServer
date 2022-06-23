package com.sammaru5.sammaru.service.user;

import com.sammaru5.sammaru.domain.UserAuthority;
import com.sammaru5.sammaru.web.dto.UserDTO;
import com.sammaru5.sammaru.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service @RequiredArgsConstructor
public class UserSearchService {
    private final UserRepository userRepository;

    public List<UserDTO> findUsers(){
        return userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public List<UserDTO> findUsersByRole(UserAuthority role) {
        return userRepository.findByRole(role).stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public List<UserDTO> findUsersByGeneration(Integer generationNum) {
        return userRepository.findByGeneration(generationNum).stream().map(UserDTO::new).collect(Collectors.toList());
    }
}
