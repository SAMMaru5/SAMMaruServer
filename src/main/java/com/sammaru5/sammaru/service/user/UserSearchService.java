package com.sammaru5.sammaru.service.user;

import com.sammaru5.sammaru.domain.UserAuthority;
import com.sammaru5.sammaru.domain.UserEntity;
import com.sammaru5.sammaru.dto.ArticleDTO;
import com.sammaru5.sammaru.dto.UserDTO;
import com.sammaru5.sammaru.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class UserSearchService {
    private final UserRepository userRepository;

    public List<UserDTO> findUsers(){
        return userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public List<UserDTO> findUsersByRole(UserAuthority role) {
        return userRepository.findByRole(role).stream().map(UserDTO::new).collect(Collectors.toList());
    }
}
