package com.pathbook.pathbook_api.dto.response;

import com.pathbook.pathbook_api.dto.LoginResultStatus;

import java.time.LocalDateTime;

public record LoginResponse(
        LoginResultStatus status,
        String message,
        UserResponse userResponse,
        LocalDateTime lockedUntil,
        LocalDateTime bannedUntil,
        String bannedReason,
        LocalDateTime deletedAt) {
    public static LoginResponse success(UserResponse userResponse) {
        return new LoginResponse(
                LoginResultStatus.SUCCESS,
                "Login successful",
                userResponse,
                null,
                null,
                null,
                null);
    }

    public static LoginResponse locked(LocalDateTime lockedUntil) {
        return new LoginResponse(
                LoginResultStatus.ACCOUNT_LOCKED,
                "Account is locked",
                null,
                lockedUntil,
                null,
                null,
                null);
    }

    public static LoginResponse invalidCredentials() {
        return new LoginResponse(
                LoginResultStatus.INVALID_CREDENTIALS,
                "Invalid email or password",
                null,
                null,
                null,
                null,
                null);
    }

    public static LoginResponse emailUnverified() {
        return new LoginResponse(
                LoginResultStatus.EMAIL_UNVERIFIED,
                "Email not verified",
                null,
                null,
                null,
                null,
                null);
    }

    public static LoginResponse banned(LocalDateTime until, String reason) {
        return new LoginResponse(
                LoginResultStatus.ACCOUNT_BANNED,
                "Account is banned",
                null,
                null,
                until,
                reason,
                null);
    }

    public static LoginResponse disabled() {
        return new LoginResponse(
                LoginResultStatus.ACCOUNT_DISABLED,
                "Account is disabled",
                null,
                null,
                null,
                null,
                null);
    }

    public static LoginResponse deleted(LocalDateTime deletedAt) {
        return new LoginResponse(
                LoginResultStatus.ACCOUNT_DELETED,
                "Account is deleted",
                null,
                null,
                null,
                null,
                deletedAt);
    }

    public static LoginResponse error(String message) {
        return new LoginResponse(
                LoginResultStatus.UNKNOWN_ERROR, message, null, null, null, null, null);
    }
}
