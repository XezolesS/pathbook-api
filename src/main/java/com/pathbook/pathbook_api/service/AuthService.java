package com.pathbook.pathbook_api.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    // TODO: DB 커넥션 체크용, 프로덕션에서는 지워야 함.
    public int testDBConnection() {
        try {
            return userRepository.testTableAccess();
        } catch (Exception ex) {
            return -1;
        }
    }

    // 로그인 (세션 방식)
    public boolean login(String email, String password, HttpSession session) {
        // 이메일로 사용자 검색
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(hashPassword(password))) {
            // 세션에 사용자 ID 저장 (보안 강화)
            session.setAttribute("userId", user.getId());
            return true;
        }
        return false;  // 로그인 실패
    }

    // 회원가입
    public String register(String username, String email, String password) {
        // 이메일 중복 확인
        if (userRepository.findByEmail(email) != null) {
            return "이미 가입된 이메일입니다.";
        }

        // 비밀번호 해싱
        String hashedPassword = hashPassword(password);

        // 랜덤 인증 토큰 생성
        String verificationToken = UUID.randomUUID().toString();

        // User 객체 저장
        User user = new User(null, username, email, hashedPassword, false, verificationToken);
        userRepository.save(user);

        // 이메일 전송
        sendVerificationEmail(email, verificationToken);

        return "회원가입 성공. 이메일을 확인하여 인증을 완료하세요.";
    }

    // 이메일 링크 전송 (인증 토큰 포함)
    private void sendVerificationEmail(String email, String verificationToken) {
        String subject = "이메일 인증";
        String body = "이메일 인증을 위해 아래 링크를 클릭하세요:\n"
                + "http://localhost:8080/auth/verify?verificationToken=" + verificationToken;
        emailService.sendEmail(email, subject, body);
    }

    // 이메일 인증 처리
    public boolean verifyEmail(String verificationToken) {
        // 인증 토큰으로 사용자 찾기
        User user = userRepository.findByVerificationToken(verificationToken);

        if (user != null) {
            // 사용자가 있으면 verified 값을 true로 변경
            user.setVerified(true);
            user.setVerificationToken(null); // 인증 완료 후 토큰 제거
            userRepository.save(user);
            return true;
        }
        return false;
    }

    // 비밀번호 해싱
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                final String hex = Integer.toHexString(0xff & hash[i]);

                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
