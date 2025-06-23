package com.pathbook.pathbook_api.service;

import com.pathbook.pathbook_api.dto.UserInfoDto;
import com.pathbook.pathbook_api.dto.UserPrincipal;
import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.exception.EmailMismatchException;
import com.pathbook.pathbook_api.exception.PasswordMismatchException;
import com.pathbook.pathbook_api.exception.UserAlreadyExistsException;
import com.pathbook.pathbook_api.exception.UserNotFoundException;
import com.pathbook.pathbook_api.jwt.EmailVerificationJwt;
import com.pathbook.pathbook_api.jwt.ResetPasswordJwt;
import com.pathbook.pathbook_api.repository.UserRepository;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.PrematureJwtException;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class AuthService {
    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private int serverPort;

    @Value("${server.web-address}")
    private String webAddress;

    @Value("${server.web-path-verify-email}")
    private String webPathVerifyEmail;

    @Value("${server.web-path-reset-password}")
    private String webPathResetPassword;

    @Autowired private UserRepository userRepository;

    @Autowired private PasswordEncoder passwordEncoder;

    @Autowired private EmailService emailService;

    @Autowired private EmailVerificationJwt emailVerificationJwt;

    @Autowired private ResetPasswordJwt resetPasswordJwt;

    /**
     * 이메일로부터 {@link UserPrincipal}을 불러옵니다.
     *
     * @param email
     * @return {@link UserPrincipal}
     */
    public UserPrincipal loadUserPrincipal(String email) {
        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> UserNotFoundException.withEmail(email));

        return new UserPrincipal(user);
    }

    /**
     * 로그인 성공을 처리합니다.
     *
     * <p>로그인 성공시 로그인 시도 횟수를 초기화하고, 로그인을 시도한 시간과 클라이언트의 IP 주소를 데이터베이스에 저장합니다.
     *
     * @param email
     * @param clientIp
     */
    public void handleLoginSuccess(String email, String clientIp) {
        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> UserNotFoundException.withEmail(email));

        user.resetFailedLoginAttempts();

        user.increaseLoginCount();
        user.setLastLoginAt(LocalDateTime.now());
        user.setLastLoginIp(clientIp);

        userRepository.save(user);
    }

    /**
     * 로그인 실패를 처리합니다.
     *
     * <p>로그인 실패시 로그인 시도 실패 횟수를 증가시킵니다. 로그인 시도 실패 횟수가 5 이상인 경우 계정 잠금 시간을 설정합니다. 계정 잠금 시간은 다음과 같이
     * 설정됩니다.
     *
     * <ul>
     *   <li>5회: 5분
     *   <li>10회: 30분
     *   <li>15회 이상 (5의 배수): 2시간
     * </ul>
     *
     * @param user
     */
    public void handleLoginFail(String email) {
        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> UserNotFoundException.withEmail(email));

        user.increaseFailedLoginAttempts();

        int failedAttempts = user.getFailedLoginAttempts();
        Duration lockedDuration = Duration.ZERO;
        if (failedAttempts == 5) {
            lockedDuration = Duration.ofMinutes(5);
        } else if (failedAttempts == 10) {
            lockedDuration = Duration.ofMinutes(30);
        } else if (failedAttempts >= 15) {
            lockedDuration = Duration.ofHours(2);
        }

        // 5의 배수일 때만 계정 잠금.
        // (0일 경우는 user.increaseFailedLoginAttempts()로 인해 불가능한 케이스이므로 예외 처리 불필요)
        if (failedAttempts % 5 == 0) {
            user.setAccountLockedUntil(LocalDateTime.now().plus(lockedDuration));
        }

        userRepository.save(user);
    }

    /**
     * 사용자를 데이터베이스에 추가합니다.
     *
     * <p>비밀번호는 {@link #passwordEncoder}를 통해 암호화됩니다.
     *
     * @param userId
     * @param username
     * @param email
     * @param password
     * @return {@link UserInfoDto} 추가된 사용자의 정보를 반환합니다.
     * @throws UserAlreadyExistsException {@code userId}, {@code email}이 중복되는 사용자가 있는 경우 예외 발생
     */
    @Transactional
    public UserInfoDto addUser(String userId, String username, String email, String password) {
        if (userRepository.existsById(userId)) {
            throw UserAlreadyExistsException.withUserId(userId);
        }

        if (userRepository.existsByEmail(email)) {
            throw UserAlreadyExistsException.withEmail(email);
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(userId, email, encodedPassword, username);

        return new UserInfoDto(userRepository.save(user));
    }

    /**
     * 사용자 인증을 위한 이메일을 전송합니다.
     *
     * @param email
     * @param password
     * @see {@link #sendVerificationEmail(User)}
     */
    public void sendVerificationEmail(String email, String password) {
        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> UserNotFoundException.withEmail(email));

        if (isPasswordMatched(user, password)) {
            throw new PasswordMismatchException(user.getId());
        }

        String userId = user.getId();

        String token = emailVerificationJwt.setUserId(userId).setEmail(email).buildToken();
        String url = String.format("%s%s?token=%s", webAddress, webPathVerifyEmail, token);

        String subject = "[Pathbook] 회원가입 이메일 인증";
        String body = "이메일 인증\n" + url;

        emailService.sendEmail(email, subject, body);
    }

    /**
     * 토큰으로부터 이메일을 인증합니다.
     *
     * <p>토큰은 JWT 형식으로 주어지며, payload 내 데이터를 통해 사용자를 검증합니다.
     *
     * @param token JWT 토큰 문자열
     * @see {@link EmailVerificationJwt}
     */
    @Transactional
    public void verifyEmail(String token) {
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

        // TODO: 토큰 사용 여부 검증

        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> UserNotFoundException.withUserId(userId));

        if (!user.getEmail().equals(email)) {
            throw new EmailMismatchException(userId, email);
        }

        user.setVerified(true);

        userRepository.save(user);
    }

    /**
     * 비밀번호 재설정이 가능한 URL를 이메일로 전송합니다.
     *
     * @param email
     */
    public void sendResetPasswordEmail(String email) {
        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() -> UserNotFoundException.withEmail(email));

        String userId = user.getId();

        String token = resetPasswordJwt.setUserId(userId).buildToken();
        String url = String.format("%s%s?token=%s", webAddress, webPathResetPassword, token);

        String subject = "[Pathbook] 비밀번호 재설정 이메일 인증";
        String body = "비밀번호 재설정\n" + url;

        emailService.sendEmail(user.getEmail(), subject, body);
    }

    /**
     * 비밀번호를 재설정합니다.
     *
     * @param token JWT 토큰 문자열
     * @param newPassword
     */
    @Transactional
    public void resetPassword(String token, String newPassword) {
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

        // TODO: 토큰 사용 여부 검증

        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> UserNotFoundException.withUserId(userId));

        String encodedPassword = passwordEncoder.encode(newPassword);

        user.setPassword(encodedPassword);
        user.resetFailedLoginAttempts();

        userRepository.save(user);
    }

    /**
     * 비밀번호를 변경합니다.
     *
     * <p>{@code oldPassword}를 통해 비밀번호 검증을 거치고 작업을 처리합니다.
     *
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @throws PasswordMismatchException
     */
    @Transactional
    public void changePassword(String userId, String oldPassword, String newPassword) {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> UserNotFoundException.withUserId(userId));

        if (!isPasswordMatched(user, oldPassword)) {
            throw new PasswordMismatchException(userId);
        }

        String encodedPassword = passwordEncoder.encode(newPassword);

        user.setPassword(encodedPassword);

        userRepository.save(user);
    }

    /**
     * 사용자를 삭제합니다.
     *
     * <p>이 기능은 soft-delete이며, hard-delete는 스케쥴러 또는 데이터베이스의 트리거를 통해 이루어집니다. (미구현)
     *
     * @param userId
     * @param password
     */
    @Transactional
    public void deleteUser(String userId, String password) {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> UserNotFoundException.withUserId(userId));

        if (!isPasswordMatched(user, password)) {
            throw new PasswordMismatchException(userId);
        }

        user.setDeletedAt(LocalDateTime.now());

        userRepository.save(user);
    }

    private boolean isPasswordMatched(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }
}
