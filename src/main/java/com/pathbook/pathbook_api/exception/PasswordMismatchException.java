package com.pathbook.pathbook_api.exception;

/** 비밀번호가 일치하지 않는 경우 예외를 발생시킵니다. */
public class PasswordMismatchException extends RuntimeException {
    private final String userId;

    public PasswordMismatchException() {
        this(null);
    }

    public PasswordMismatchException(String userId) {
        super("Password mismatched");

        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
