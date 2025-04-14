package com.pathbook.pathbook_api.dto;

public record LikeRequest(String userId, Long commentId, boolean like) {}
