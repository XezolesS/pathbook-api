package com.pathbook.pathbook_api.entity.id;

import java.io.Serializable;
import java.util.Objects;

public class UserPostReportId implements Serializable {
    private String user;
    private Long post;

    public UserPostReportId() {}

    public UserPostReportId(String user, Long post) {
        this.user = user;
        this.post = post;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof UserPostReportId)) {
            return false;
        }

        UserPostReportId that = (UserPostReportId) object;
        return Objects.equals(user, that.user) && Objects.equals(post, that.post);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, post);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getPost() {
        return post;
    }

    public void setPost(Long post) {
        this.post = post;
    }
}
