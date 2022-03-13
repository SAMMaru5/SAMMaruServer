package com.sammaru5.sammaru.service.user;

import com.sammaru5.sammaru.domain.UserEntity;
import com.sammaru5.sammaru.repository.UserRepository;
import com.sammaru5.sammaru.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserStatusService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public Optional<UserEntity> findUser(HttpServletRequest request) {
        String token = jwtTokenProvider.getTokenFromRequest(request);
        Long userId = jwtTokenProvider.getIdFromToken(token);
        return userRepository.findById(userId);
    }
}
