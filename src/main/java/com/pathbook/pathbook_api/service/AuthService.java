package com.pathbook.pathbook_api.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import com.pathbook.pathbook_api.JwtValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    protected JwtValidator JwtValidator;

    // TODO: DB 커넥션 체크용, 프로덕션에서는 지워야 함.
    public int testDBConnection() {
        try {
            return userRepository.testTableAccess();
        } catch (Exception ex) {
            return -1;
        }
    }

    // 로그인
    public String login(String email, String password) {
        // 이메일로 사용자 검색
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(hashPassword(password))) {
            // 비밀번호가 맞다면 JWT 생성 후 반환
            return JwtValidator.generateToken(email);  // JWT 토큰 발급
        }
        return null;  // 로그인 실패
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
