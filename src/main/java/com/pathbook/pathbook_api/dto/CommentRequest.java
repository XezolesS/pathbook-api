package com.pathbook.pathbook_api.dto;

public record CommentRequest(Long postId, String authorId, String content) {}
