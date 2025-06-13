package com.pathbook.pathbook_api.entity;

import com.pathbook.pathbook_api.entity.id.UserPostReportId;

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
@Table(name = "user_post_reports")
@IdClass(UserPostReportId.class)
public class UserPostReport {
    // region Fields

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

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

    // endregion

    // region Constructors

    protected UserPostReport() {}

    public UserPostReport(User reporter, Post post, String reason, String message) {
        this.reporter = reporter;
        this.post = post;
        this.reason = reason;
        this.message = message;
    }

    // endregion

    // region Getters & Setters

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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

    // endregion

    // region Events

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // endregion
}
