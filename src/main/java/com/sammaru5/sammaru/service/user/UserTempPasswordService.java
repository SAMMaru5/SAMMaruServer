package com.sammaru5.sammaru.service.user;

import com.sammaru5.sammaru.domain.User;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class UserTempPasswordService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String sendTempPassword(String email) {
        Optional<User> findUser = userRepository.findByEmail(email);
        if (findUser.isEmpty()) throw new CustomException(ErrorCode.USER_NOT_FOUND, email);
        User user = findUser.get();

        String tempPassword = getRandomPassword((int) (Math.random() * 13) + 8);
        user.setPassword(passwordEncoder.encode(tempPassword));

        // TODO : 메일 발송
        return tempPassword;
    }

    public String getRandomPassword(int size) {
        char[] charSet = new char[]{
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '!', '@', '#', '$', '%', '^', '&'
        };
        StringBuilder sb = new StringBuilder();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());

        for (int i = 0; i < size; i++) {
            int idx = sr.nextInt(charSet.length);
            sb.append(charSet[idx]);
        }
        return sb.toString();
    }
}
