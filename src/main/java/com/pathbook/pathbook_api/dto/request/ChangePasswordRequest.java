package com.pathbook.pathbook_api.dto.request;

public record ChangePasswordRequest(String oldPassword, String newPassword) {}
