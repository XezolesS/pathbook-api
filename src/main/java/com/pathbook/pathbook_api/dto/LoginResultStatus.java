package com.pathbook.pathbook_api.dto;

/**
 * 로그인 결과 상태를 나타내는 enum입니다.
 *
 * @param statusCode
 */
public enum LoginResultStatus {
    /** 로그인 성공 */
    SUCCESS("SUCCESS"),

    /** 계정 잠금으로 인한 로그인 실패 */
    ACCOUNT_LOCKED("ACCOUNT_LOCKED"),

    /** 로그인 정보 불일치로 인한 로그인 실패 */
    INVALID_CREDENTIALS("INVALID_CREDENTIALS"),

    /** 이메일 미인증으로 인한 로그인 실패 */
    EMAIL_UNVERIFIED("EMAIL_UNVERIFIED"),

    /** 계정 정지로 인한 로그인 실패 */
    ACCOUNT_BANNED("ACCOUNT_BANNED"),

    /** 계정 비활성화로 인한 로그인 실패 */
    ACCOUNT_DISABLED("ACCOUNT_DISABLED"),

    /** 계정 삭제로 인한 로그인 실패 (Soft-delete) */
    ACCOUNT_DELETED("ACCOUNT_DELETED"),

    /** 오류로 인한 로그인 실패 */
    UNKNOWN_ERROR("UNKNOWN_ERROR");

    private final String statusCode;

    LoginResultStatus(final String statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return statusCode;
    }
}
