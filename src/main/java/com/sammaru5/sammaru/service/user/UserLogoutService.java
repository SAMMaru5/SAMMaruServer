package com.sammaru5.sammaru.service.user;

import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.repository.UserRepository;
import com.sammaru5.sammaru.util.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class UserLogoutService {
    private final UserRepository userRepository;
    private final RedisUtil redisUtil;

    public Boolean deleteRefreshToken(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND, userId.toString());
        }

        redisUtil.deleteData("RT:" + userId);

        return true;
    }
}
