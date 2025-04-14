package com.pathbook.pathbook_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_comment_like")
public class UserCommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "comment_id", nullable = false)
    private Long commentId;

    @Column(name = "like", nullable = false)
    private boolean like;

    public UserCommentLike() {}

    public UserCommentLike(String userId, Long commentId, boolean like) {
        this.userId = userId;
        this.commentId = commentId;
        this.like = like;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }
}
