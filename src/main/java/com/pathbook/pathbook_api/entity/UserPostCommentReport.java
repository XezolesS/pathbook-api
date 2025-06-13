package com.pathbook.pathbook_api.entity;

import com.pathbook.pathbook_api.entity.id.UserPostCommentReportId;

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
@Table(name = "user_post_comment_reports")
@IdClass(UserPostCommentReportId.class)
public class UserPostCommentReport {
    // region Fields

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private PostComment comment;

    @Column(name = "reason", columnDefinition = "TINYTEXT", nullable = false)
    private String reason;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // endregion

    // region Constructors

    protected UserPostCommentReport() {}

    public UserPostCommentReport(User reporter, PostComment comment, String reason, String message) {
        this.reporter = reporter;
        this.comment = comment;
        this.reason = reason;
        this.message = message;
    }

    public UserPostCommentReport(
            User reporter,
            PostComment comment,
            String reason,
            String message,
            LocalDateTime createdAt) {
        this.reporter = reporter;
        this.comment = comment;
        this.reason = reason;
        this.message = message;
        this.createdAt = createdAt;
    }

    // endregion

    // region Getters & Setters

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public PostComment getComment() {
        return comment;
    }

    public void setComment(PostComment comment) {
        this.comment = comment;
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
