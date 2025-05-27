package com.pathbook.pathbook_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comment_report")
public class CommentReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Comment comment;

    @ManyToOne(optional = false)
    private User reporter;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportReason reason;

    @Column(length = 500)
    private String detailReason;

    protected CommentReport() {}

    public CommentReport(Comment comment, User reporter, ReportReason reason, String detailReason) {
        this.comment = comment;
        this.reporter = reporter;
        this.reason = reason;
        this.detailReason = detailReason;
    }
}
