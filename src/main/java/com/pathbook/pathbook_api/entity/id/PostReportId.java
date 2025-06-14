package com.pathbook.pathbook_api.entity.id;

import java.io.Serializable;
import java.util.Objects;

public class PostReportId implements Serializable {
    private String reporter;
    private Long post;

    public PostReportId() {}

    public PostReportId(String reporter, Long post) {
        this.reporter = reporter;
        this.post = post;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof PostReportId)) {
            return false;
        }

        PostReportId that = (PostReportId) object;
        return Objects.equals(reporter, that.reporter) && Objects.equals(post, that.post);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reporter, post);
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public Long getPost() {
        return post;
    }

    public void setPost(Long post) {
        this.post = post;
    }
}
