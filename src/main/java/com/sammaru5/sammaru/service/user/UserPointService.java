package com.sammaru5.sammaru.service.user;

import com.sammaru5.sammaru.domain.UserEntity;
import com.sammaru5.sammaru.exception.InvalidPointException;
import com.sammaru5.sammaru.repository.UserRepository;
import com.sammaru5.sammaru.request.PointRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPointService {
    private final UserRepository userRepository;

    public UserEntity addPoint(Long userId, PointRequest pointRequest) throws InvalidPointException {
        UserEntity userEntity = userRepository.getById(userId);
        if(userEntity.getPoint() + pointRequest.getAddPoint() < 0){
            throw new InvalidPointException();
        }
        userEntity.setPoint(userEntity.getPoint() + pointRequest.getAddPoint());
        return userRepository.save(userEntity);
    }
}
