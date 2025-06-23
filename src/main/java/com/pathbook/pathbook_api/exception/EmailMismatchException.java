package com.pathbook.pathbook_api.exception;

/** 이메일이 일치하지 않는 경우 예외를 발생시킵니다. */
public class EmailMismatchException extends RuntimeException {
    private final String userId;
    private final String email;

    public EmailMismatchException() {
        this(null);
    }

    public EmailMismatchException(String userId) {
        this(userId, null);
    }

    public EmailMismatchException(String userId, String email) {
        super("Email mismatched");

        this.userId = userId;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }
}
