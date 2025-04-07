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

    /**
     * 사용자를 데이터베이스에 추가합니다.
     * 
     * @param id 사용자 ID
     * @param username 사용자 이름
     * @param email 사용자 이메일
     * @param password 사용자 비밀번호. 암호화된 비밀번호만 저장해야 합니다.
     * 
     * @return 성공적으로 추가된 경우는 true, 실패한 경우는 false.
     */
    public boolean addUser(String id, String username, String email, String password) {
        if (userRepository.findByEmail(email) != null) {
            return false;
        }

        User user = new User(id, username, email, password, false);
        userRepository.save(user);

        return true;
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
