package com.sammaru5.sammaru.service.user;


import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.repository.UserRepository;
import com.sammaru5.sammaru.util.CacheKey;
import com.sammaru5.sammaru.util.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import java.util.Random;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserEmailVerifyService {

    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;
    private final UserRepository userRepository;

    public Boolean sendVerificationCode(String userEmail) {

        if( isNotProperEmail(userEmail)){
            throw new CustomException(ErrorCode.VALID_CHECK_FAIL, userEmail);
        }

        expirePreviousVerificationCode(userEmail);

        String verificationCode = getVerificationCode();

        saveVerificationCode(verificationCode, userEmail);

        sendMail(userEmail, verificationCode);

        return true;
    }

    public Boolean verifyEmail(String verificationCode) {
        if (!validateVerificationCode(verificationCode)){
            throw new CustomException(ErrorCode.INVALID_VERIFICATION_CODE);
        }
        saveTempVerifiedEmail(verificationCode);
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
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            message.addRecipients(Message.RecipientType.TO, userEmail);
            message.setSubject("SAMMaru 인증 코드");
            String htmlStr =
                    "<div>" +
                    "  <h3>SAMMaru</h3>\n" +
                    "  <div><p>다음 인증코드를 입력해주세요.</p><p>인증코드: <span style=\"color:blue\">"
                    + verificationCode +
                    "</span></p></div>" +
                    "</div>";
            message.setText(htmlStr,"UTF-8", "html");
            javaMailSender.send(message);
        } catch (Exception e ){
            e.printStackTrace();
        }
    }

    private boolean validateVerificationCode(String verificationCode) {
        return redisUtil.hasKey(verificationCode);
    }

    private void saveVerificationCode(String verificationCode, String userEmail) {
        redisUtil.setDataExpire(verificationCode, userEmail, CacheKey.VERIFICATION_CODE_EXPIRE_SEC);
        redisUtil.setDataExpire(userEmail,verificationCode, CacheKey.VERIFICATION_CODE_EXPIRE_SEC);
    }

    @Transactional(readOnly = true)
    public boolean isNotProperEmail(String userEmail){
        if(userRepository.existsByEmail(userEmail)){
            throw new CustomException(ErrorCode.ALREADY_EXIST_EMAIL, userEmail);
        }
        return userEmail == null ||
                !(userEmail.contains("@gmail.com") || userEmail.contains("@naver.com"));
    }

    private void saveTempVerifiedEmail(String verificationCode){
        String userEmail = redisUtil.getData(verificationCode);
        redisUtil.deleteData(userEmail);
        redisUtil.deleteData(verificationCode);
        redisUtil.setDataExpire(userEmail+":auth", verificationCode, CacheKey.TEMP_EMAIL_EXPIRE_SEC);
    }

    private void expirePreviousVerificationCode(String userEmail) {
        String verificationCode = redisUtil.getData(userEmail);
        if(verificationCode == null || verificationCode.isEmpty()){
            return;
        }
        //이전에 발급된 인증번호 삭제
        redisUtil.deleteData(userEmail);
        redisUtil.deleteData(verificationCode);
    }
}

