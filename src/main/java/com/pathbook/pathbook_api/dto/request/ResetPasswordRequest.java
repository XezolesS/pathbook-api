package com.pathbook.pathbook_api.dto.request;

public record ResetPasswordRequest(String token, String newPassword) {}
