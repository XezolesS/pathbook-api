package com.pathbook.pathbook_api.entity;

import com.pathbook.pathbook_api.dto.ReportReason;

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
@Table(name = "post_report")
public class PostReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Post post;

    @ManyToOne(optional = false)
    private User reporter;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportReason reason;

    @Column(length = 500)
    private String detailReason;

    protected PostReport() {}

    public PostReport(Post post, User reporter, ReportReason reason, String detailReason) {
        this.post = post;
        this.reporter = reporter;
        this.reason = reason;
        this.detailReason = detailReason;
    }
}
