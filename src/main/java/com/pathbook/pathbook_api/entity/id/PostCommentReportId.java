package com.pathbook.pathbook_api.entity.id;

import java.io.Serializable;
import java.util.Objects;

public class PostCommentReportId implements Serializable {
    private String reporter;
    private Long comment;

    public PostCommentReportId() {}

    public PostCommentReportId(String reporter, Long comment) {
        this.reporter = reporter;
        this.comment = comment;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof PostCommentReportId)) {
            return false;
        }

        PostCommentReportId that = (PostCommentReportId) object;
        return Objects.equals(reporter, that.reporter) && Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reporter, comment);
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public Long getComment() {
        return comment;
    }

    public void setComment(Long comment) {
        this.comment = comment;
    }
}
