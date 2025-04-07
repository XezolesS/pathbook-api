package com.pathbook.pathbook_api.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.entity.UserVerifyToken;
import com.pathbook.pathbook_api.repository.UserRepository;
import com.pathbook.pathbook_api.repository.UserVerifyTokenRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserVerifyTokenRepository userVerifyTokenRepository;

    @Autowired
    private EmailService emailService;

    /**
     * 사용자를 데이터베이스에 추가합니다.
     * 동시에 사용자 인증을 위한 이메일도 전송합니다.
     * 
     * @param id 사용자 ID.
     * @param username 사용자 이름.
     * @param email 사용자 이메일.
     * @param password 사용자 비밀번호. 암호화된 비밀번호만 저장해야 합니다.
     * 
     * @return 성공적으로 추가된 경우는 true, 실패한 경우는 false.
     */
    @Transactional
    public boolean addUser(String id, String username, String email, String password) {
        if (userRepository.findByEmail(email) != null) {
            return false;
        }

        User user = new User(id, username, email, password, false);
        User savedUser = userRepository.save(user);

        // 랜덤 토큰 생성
        // 임시이므로 추후 필요시에 변경
        final String tokenChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder tokenBuilder = new StringBuilder();
        while (tokenBuilder.length() < 32) {
            int randomInt = random.nextInt(tokenChars.length());
            tokenBuilder.append(tokenChars.charAt(randomInt));
        }

        String token = tokenBuilder.toString();
        
        // 토큰 저장
        userVerifyTokenRepository.save(
            new UserVerifyToken(
                savedUser,
                token,
                LocalDateTime.now().plusDays(1),
                false
            )
        );

        // 이메일 전송
        sendVerificationEmail(savedUser, token);

        return true;
    }

    /**
     * 사용자 인증을 위한 이메일을 전송합니다.
     * 
     * @param user 사용자 엔티티.
     * @param token 인증 토큰.
     */
    private void sendVerificationEmail(User user, String token) {
        String subject = "[Pathbook] 회원가입 이메일 인증";
        String body = "이메일 인증을 위해 아래 링크를 클릭하세요:\n"
            + "http://localhost:8080/auth/verify?"
            + "id=" + user.getId() + "&"
            + "token=" + token;
        
        emailService.sendEmail(user.getEmail(), subject, body);
    }

    @Transactional
    public boolean verifyUser(String userId, String token) {
        UserVerifyToken userVerifyToken = userVerifyTokenRepository.findById(token).get();

        // TODO: 각 조건에 대한 예외 처리
        if (userVerifyToken == null || 
            userVerifyToken.getExpiresAt().isBefore(LocalDateTime.now()) ||
            userVerifyToken.isUsed() ||
            userVerifyToken.getUser().getId().equals(userId) == false) {
            return false;
        }
        
        User user = userVerifyToken.getUser();

        user.setVerified(true);
        userRepository.save(user);

        userVerifyToken.setUsed(true);
        userVerifyTokenRepository.save(userVerifyToken);

        return true;
    }

}
