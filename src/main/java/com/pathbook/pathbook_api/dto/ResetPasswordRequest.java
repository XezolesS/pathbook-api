package com.pathbook.pathbook_api.dto;

public record ResetPasswordRequest(String token, String newPassword) {

}
