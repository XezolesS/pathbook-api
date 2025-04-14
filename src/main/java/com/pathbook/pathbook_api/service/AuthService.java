package com.pathbook.pathbook_api.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.entity.UserVerifyToken;
import com.pathbook.pathbook_api.repository.UserRepository;
import com.pathbook.pathbook_api.repository.UserVerifyTokenRepository;
import com.pathbook.pathbook_api.entity.AccountLockStatus;
import com.pathbook.pathbook_api.repository.AccountLockStatusRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserVerifyTokenRepository userVerifyTokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountLockStatusRepository accountLockStatusRepository;

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
        String token = generateToken();

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

    @Transactional
    public boolean sendResetPasswordEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }

        String token = generateToken();
        userVerifyTokenRepository.save(
            new UserVerifyToken(
                user,
                token,
                LocalDateTime.now().plusDays(1),
                false
            )
        );

        sendResetPasswordEmail(user, token);
        return true;
    }

    private void sendResetPasswordEmail(User user, String token) {
        String subject = "[Pathbook] 비밀번호 재설정 이메일 인증";
        String body = "이메일 인증을 위해 아래 링크를 클릭하세요:\n"
            + "http://localhost:8080/auth/reset-password-form?"
            + "id=" + user.getId() + "&"
            + "token=" + token;

        emailService.sendEmail(user.getEmail(), subject, body);
    }

    @Transactional
    public boolean resetPassword(String userId, String token, String newpassword) {
        UserVerifyToken userVerifyToken = userVerifyTokenRepository.findById(token).get();

        if (userVerifyToken == null ||
            userVerifyToken.getExpiresAt().isBefore(LocalDateTime.now()) ||
            userVerifyToken.isUsed() ||
            userVerifyToken.getUser().getId().equals(userId) == false) {
            return false;
        }
        User user = userVerifyToken.getUser();
        user.setPassword(passwordEncoder.encode(newpassword));
        userVerifyToken.setUsed(true);

        AccountLockStatus lockStatus = accountLockStatusRepository.findByUserId(user.getId());
        if (lockStatus == null) {
            lockStatus = new AccountLockStatus(user.getId());
        }
        lockStatus.setLocked(true);
        lockStatus.setFailedAttempts(0);

        userRepository.save(user);
        userVerifyTokenRepository.save(userVerifyToken);
        accountLockStatusRepository.save(lockStatus);
        return true;
    }

    private String generateToken() {
        // 랜덤 토큰 생성
        // 임시이므로 추후 필요시에 변경
        final String tokenChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder tokenBuilder = new StringBuilder();
        while (tokenBuilder.length() < 32) {
            int randomInt = random.nextInt(tokenChars.length());
            tokenBuilder.append(tokenChars.charAt(randomInt));
        }
        return tokenBuilder.toString();
    }


    public boolean handleLogin(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }

        AccountLockStatus lockStatus = accountLockStatusRepository.findByUserId(user.getId());
        if (lockStatus == null) {
            lockStatus = new AccountLockStatus(user.getId());
        }

        if (lockStatus.isLocked()) {
            return false;
        }

        boolean isPasswordValid = passwordEncoder.matches(password, user.getPassword());
        if (isPasswordValid) {
            lockStatus.setFailedAttempts(0);
            accountLockStatusRepository.save(lockStatus);
            return true;
        } else {
            lockStatus.setFailedAttempts(lockStatus.getFailedAttempts() + 1);

            if (lockStatus.getFailedAttempts() >= 5) {
                lockStatus.setLocked(true);

                String token = generateToken();

                userVerifyTokenRepository.save(
                    new UserVerifyToken(
                        user,
                        token,
                        LocalDateTime.now().plusDays(1),
                        false
                    )
                );
                sendResetPasswordEmail(user, token);
            }
            accountLockStatusRepository.save(lockStatus);
            return false;
        }
    }
}