package com.pathbook.pathbook_api.request;

public record ResetPasswordRequest(String token, String newPassword) {}
