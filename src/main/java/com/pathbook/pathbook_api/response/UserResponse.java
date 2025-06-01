package com.pathbook.pathbook_api.response;

public record UserResponse(String userId, String username, String email, boolean verified) {}
