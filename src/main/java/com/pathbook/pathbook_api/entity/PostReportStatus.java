package com.pathbook.pathbook_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "post_report_status")
public class PostReportStatus {

    @Id
    private Long postId;

    @Column(nullable = false)
    private int reportCount = 0;

    @Column(nullable = false)
    private boolean isHidden = false;

    protected PostReportStatus() {}

    public PostReportStatus(long postId) {
        this.postId = postId;
    }

    public int getReportCount() {
        return this.reportCount;
    }

    public boolean isHidden() {
        return this.isHidden;
    }

    public void setReportCount(int reportCount) {
        this.reportCount = reportCount;
    }

    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }
}
