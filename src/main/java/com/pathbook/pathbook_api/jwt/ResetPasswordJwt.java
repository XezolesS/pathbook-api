package com.pathbook.pathbook_api.jwt;

import org.springframework.stereotype.Component;

/**
 * 비밀번호 재설정시 사용자 검증에 사용되는 JWT 입니다.
 *
 * <ul>
 *   <li>Subject: {@code reset-password}
 *   <li>유효시간: 30분
 * </ul>
 */
@Component
public class ResetPasswordJwt extends JwtBase {
    public static final String USER_ID = "user_id";

    public ResetPasswordJwt() {
        super("reset-password", 60 * 30);
    }

    public ResetPasswordJwt setUserId(String userId) {
        return (ResetPasswordJwt) setClaim(USER_ID, userId);
    }

    public String getUserId() {
        return (String) getClaim(USER_ID);
    }
}
