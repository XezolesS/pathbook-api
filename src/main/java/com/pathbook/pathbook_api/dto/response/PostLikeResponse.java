package com.pathbook.pathbook_api.dto.response;

import com.pathbook.pathbook_api.entity.PostLike;
import com.pathbook.pathbook_api.entity.User;

import java.time.LocalDateTime;

public class PostLikeResponse {
    private User user;
    private LocalDateTime createdAt;

    public PostLikeResponse(PostLike postLike) {
        this.user = postLike.getUser();
        this.createdAt = postLike.getCreatedAt();
    }

    public PostLikeResponse(User user, LocalDateTime createdAt) {
        this.user = user;
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
