package com.pathbook.pathbook_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "comment_report_status")
public class CommentReportStatus {

    @Id
    private Long commentId;

    @Column(nullable = false)
    private int reportCount = 0;

    @Column(nullable = false)
    private boolean hidden = false;

    protected CommentReportStatus() {
    }

    public CommentReportStatus(Long commentId) {
        this.commentId = commentId;
    }

    public int getReportCount() {
        return this.reportCount;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public void setReportCount(int reportCount) {
        this.reportCount = reportCount;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

}
