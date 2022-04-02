package com.sammaru5.sammaru.service.user;

import com.sammaru5.sammaru.apiresult.ApiResult;
import com.sammaru5.sammaru.domain.UserEntity;
import com.sammaru5.sammaru.dto.UserDTO;
import com.sammaru5.sammaru.repository.UserRepository;
import com.sammaru5.sammaru.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserModifyService {
    private final UserRepository userRepository;
    private final UserSearchService userSearchService;
    private final PasswordEncoder passwordEncoder;

    public UserDTO modifyUser(Authentication authentication, UserRequest userRequest) throws Exception {

        try{
            UserEntity userEntity = userSearchService.getUserFromToken(authentication);
            if(userRequest.getUsername() != null){
                userEntity.setUsername(userRequest.getUsername());
            }
            if(userRequest.getEmail() != null){
                userEntity.setEmail(userRequest.getEmail());
            }
            if(userRequest.getPassword() != null){
                userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            }
            return new UserDTO(userRepository.save(userEntity));
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }

    }
}
