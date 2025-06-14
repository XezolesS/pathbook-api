package com.pathbook.pathbook_api.dto.response;

public record UserResponse(String userId, String username, String email, boolean verified) {}
