package com.pathbook.pathbook_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pathbook.pathbook_api.TokenStore;
import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenStore tokenStore;

    // TODO: DB 커넥션 체크용, 프로덕션에서는 지워야 함.
    public int testDBConnection() {
        try {
            return userRepository.testTableAccess();
        } catch (Exception ex) {
            return -1;
        }
    }

    public String register(String id, String username, String email, String password) {
        if (userRepository.findByEmail(email) != null) {
            return "이미 가입된 이메일입니다.";
        }

        User user = new User(id, username, email, password, false);
        userRepository.save(user);

        // String verificationToken = UUID.randomUUID().toString();
        // tokenStore.storeToken(user.getId(), verificationToken);
        // sendVerificationEmail(email, verificationToken);

        return "회원가입 성공. 이메일을 확인하여 인증을 완료하세요.";
    }

    private void sendVerificationEmail(String email, String verificationToken) {
        String subject = "이메일 인증";
        String body = "이메일 인증을 위해 아래 링크를 클릭하세요:\n"
            + "http://localhost:8080/auth/verify?verificationToken=" + verificationToken;
        emailService.sendEmail(email, subject, body);
    }

    // 이메일 인증 처리
    public boolean verifyEmail(String email, String verificationToken) {
        String storedToken = tokenStore.getVerificationToken(email);

        if (storedToken != null && storedToken.equals(verificationToken)) {
            User user = userRepository.findByEmail(email);
            user.setVerified(true);
            userRepository.save(user);
            tokenStore.removeVerificationToken(email);
            return true;
        }

        return false;
    }

}
