package com.pathbook.pathbook_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pathbook.pathbook_api.entity.AccountLockStatus;
import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.exception.UserNotFoundException;
import com.pathbook.pathbook_api.jwt.EmailVerificationJwt;
import com.pathbook.pathbook_api.jwt.ResetPasswordJwt;
import com.pathbook.pathbook_api.repository.AccountLockStatusRepository;
import com.pathbook.pathbook_api.repository.UserRepository;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.PrematureJwtException;
import jakarta.transaction.Transactional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountLockStatusRepository accountLockStatusRepository;

    @Autowired
    private EmailVerificationJwt emailVerificationJwt;

    @Autowired
    private ResetPasswordJwt resetPasswordJwt;

    /**
     * 사용자를 데이터베이스에 추가합니다.
     * 동시에 사용자 인증을 위한 이메일도 전송합니다.
     * 
     * @param id       사용자 ID.
     * @param username 사용자 이름.
     * @param email    사용자 이메일.
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

        sendVerificationEmail(savedUser);

        return true;
    }

    @Transactional
    public void verifyUserEmail(String token) {
        try {
            emailVerificationJwt.verify(token);
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (PrematureJwtException ex) {
            throw ex;
        } catch (JwtException ex) {
            throw ex;
        }

        String userId = emailVerificationJwt.getUserId();
        String email = emailVerificationJwt.getEmail();

        if (userId == null || email == null) {
            // Suggest: MissingTokenException, InvalidTokenException
            throw new RuntimeException("Token is missing required claims");
        }

        // TODO: 토큰 사용 여부 검증

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Cannot find user with id: " + userId));

        if (!user.getEmail().equals(email)) {
            // Suggest: EmailMismatchException
            throw new RuntimeException("Email is not matched");
        }

        // TODO: Check if the email is already verified

        user.setVerified(true);
        userRepository.save(user);
    }

    @Transactional
    public void requestPasswordReset(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException("Cannot find user with email " + email);
        }

        sendResetPasswordEmail(user);
    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        ResetPasswordJwt resetPasswordJwt = new ResetPasswordJwt();

        try {
            resetPasswordJwt.verify(token);
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (PrematureJwtException ex) {
            throw ex;
        } catch (JwtException ex) {
            throw ex;
        }

        String userId = resetPasswordJwt.getUserId();

        if (userId == null) {
            throw new RuntimeException("userId is null");
        }

        // TODO: 토큰 사용 여부 검증

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Cannot find user with id: " + userId));

        user.setPassword(newPassword);

        AccountLockStatus lockStatus = accountLockStatusRepository.findByUserId(user.getId());
        if (lockStatus == null) {
            lockStatus = new AccountLockStatus(user.getId());
        }

        lockStatus.setLocked(true);
        lockStatus.setFailedAttempts(0);

        userRepository.save(user);
        accountLockStatusRepository.save(lockStatus);
    }

    public boolean handleLogin(String email, String password) {
        // TODO: 로그인 로직 단순화
        // FIXME: 비밀번호 실패 5회 째, 계정 잠금처리에 문제가 있어보임. (500 코드 반환)
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
                sendResetPasswordEmail(user);
            }
            accountLockStatusRepository.save(lockStatus);
            return false;
        }
    }

    /**
     * 사용자 인증을 위한 이메일을 전송합니다.
     * 
     * @param user  사용자 엔티티.
     * @param token 인증 토큰.
     */
    private void sendVerificationEmail(User user) {
        String token = emailVerificationJwt
                .setUserId(user.getId())
                .setEmail(user.getEmail())
                .buildToken();

        String subject = "[Pathbook] 회원가입 이메일 인증";
        String body = "이메일 인증을 위해 아래 링크를 클릭하세요:\n"
                + "http://localhost:8080/auth/verify?"
                + "token=" + token;

        emailService.sendEmail(user.getEmail(), subject, body);
    }

    private void sendResetPasswordEmail(User user) {
        String token = resetPasswordJwt
                .setUserId(user.getId())
                .buildToken();

        String subject = "[Pathbook] 비밀번호 재설정 이메일 인증";
        String body = "이메일 인증을 위해 아래 링크를 클릭하세요:\n"
                + "http://localhost:8080/auth/reset-password-form?"
                + "token=" + token;

        emailService.sendEmail(user.getEmail(), subject, body);
    }

}
