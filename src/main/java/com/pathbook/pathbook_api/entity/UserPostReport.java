package com.pathbook.pathbook_api.entity;

import com.pathbook.pathbook_api.entity.id.UserPostReportId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_post_reports")
@IdClass(UserPostReportId.class)
public class UserPostReport {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "reason", columnDefinition = "TINYTEXT", nullable = false)
    private String reason;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    protected UserPostReport() {}

    public UserPostReport(User user, Post post, String reason, String message) {
        this.user = user;
        this.post = post;
        this.reason = reason;
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }

    public UserPostReport(
            User user, Post post, String reason, String message, LocalDateTime createdAt) {
        this.user = user;
        this.post = post;
        this.reason = reason;
        this.message = message;
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
