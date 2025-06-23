package com.pathbook.pathbook_api.dto;

/**
 * 로그인 결과 상태를 나타내는 enum입니다.
 *
 * @param statusCode
 */
public enum AccountStatus {
    /** 계정 활성화 상태 */
    ACTIVE("ACTIVE"),

    /** 계정 이메일 미인증 상태 */
    UNVERIFIED("UNVERIFIED"),

    /** 계정 잠금 상태 */
    LOCKED("LOCKED"),

    /** 계정 정지 상태 */
    BANNED("BANNED"),

    /** 계정 비활성화 상태 */
    DISABLED("DISABLED"),

    /** 계정 삭제 상태 (Soft-delete) */
    DELETED("DELETED");

    private final String statusCode;

    AccountStatus(final String statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return statusCode;
    }
}
