package com.pathbook.pathbook_api.jwt;


import org.springframework.stereotype.Component;

/**
 * 사용자 이메일 검증에 사용되는 JWT 입니다.
 *
 * <ul>
 *   <li>Subject: {@code verify-email}
 *   <li>유효시간: 2시간
 * </ul>
 */
@Component
public class EmailVerificationJwt extends JwtBase {
    public static final String USER_ID = "user_id";
    public static final String EMAIL = "email";

    public EmailVerificationJwt() {
        super("verify-email", 60 * 60 * 2);
    }

    public EmailVerificationJwt setUserId(String userId) {
        return (EmailVerificationJwt) setClaim(USER_ID, userId);
    }

    public String getUserId() {
        return (String) getClaim(USER_ID);
    }

    public EmailVerificationJwt setEmail(String email) {
        return (EmailVerificationJwt) setClaim(EMAIL, email);
    }

    public String getEmail() {
        return (String) getClaim(EMAIL);
    }
}
