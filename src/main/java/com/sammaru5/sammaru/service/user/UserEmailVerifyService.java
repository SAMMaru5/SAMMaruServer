package com.sammaru5.sammaru.service.user;


import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.util.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserEmailVerifyService {

    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;

    public Boolean sendVerificationCode(String userEmail) {

        String verificationCode = getVerificationCode();

        saveVerificationCode(userEmail, verificationCode);

        sendMail(userEmail, verificationCode);

        return true;
    }

    public Boolean verifyEmail(String verificationCode) {
        if (!validateVerificationCode(verificationCode)){
            throw new CustomException(ErrorCode.INVALID_VERIFICATION_CODE);
        }
        deleteVerificationCode(verificationCode);
        return true;
    }

    private String getVerificationCode(){
        Random random = new Random();

        StringBuilder code = new StringBuilder();
        for (int j = 0; j < 6; j++) {
            int randomInt = random.nextInt(36);
            char c = (randomInt < 10) ? (char)('0' + randomInt) : (char)('A' + randomInt - 10);
            code.append(c);
        }
        return code.toString();
    }

    private void sendMail(String userEmail, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("인증 코드");
        message.setText(verificationCode);

        javaMailSender.send(message);
    }

    private boolean validateVerificationCode(String verificationCode) {
        return redisUtil.hasKey(verificationCode);
    }

    private void saveVerificationCode(String userEmail, String verificationCode) {
        redisUtil.setDataExpire(verificationCode, userEmail, 300);
    }
    private void deleteVerificationCode(String verificationCode) {
        redisUtil.deleteData(verificationCode);
    }
}

