package com.pathbook.pathbook_api.entity;

import com.pathbook.pathbook_api.entity.id.UserPostCommentLikeId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_post_comment_likes")
@IdClass(UserPostCommentLikeId.class)
public class UserPostCommentLike {
    // region Fields

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private PostComment comment;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // endregion

    // region Constructors

    protected UserPostCommentLike() {}

    public UserPostCommentLike(User user, PostComment comment) {
        this.user = user;
        this.comment = comment;
    }

    // endregion

    // region Getters & Setters

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PostComment getComment() {
        return comment;
    }

    public void setComment(PostComment comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // endregion

    // region Events

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // endreigon
}
