package com.sammaru5.sammaru.service.user;

import com.sammaru5.sammaru.domain.UserAuthority;
import com.sammaru5.sammaru.domain.UserEntity;
import com.sammaru5.sammaru.dto.UserDTO;
import com.sammaru5.sammaru.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserRoleService {
    private final UserRepository userRepository;

    public UserDTO changeRole(Long userId, UserAuthority role){
        UserEntity userEntity = userRepository.findById(userId).get();
        userEntity.setRole(role);
        return new UserDTO(userRepository.save(userEntity));
    }
}
