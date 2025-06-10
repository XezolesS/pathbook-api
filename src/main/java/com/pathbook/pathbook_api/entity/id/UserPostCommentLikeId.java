package com.pathbook.pathbook_api.entity.id;

import java.io.Serializable;
import java.util.Objects;

public class UserPostCommentLikeId implements Serializable {
    private String user;
    private Long comment;

    public UserPostCommentLikeId() {}

    public UserPostCommentLikeId(String user, Long comment) {
        this.user = user;
        this.comment = comment;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof UserPostCommentLikeId)) {
            return false;
        }

        UserPostCommentLikeId that = (UserPostCommentLikeId) object;
        return Objects.equals(user, that.user) && Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, comment);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getComment() {
        return comment;
    }

    public void setComment(Long comment) {
        this.comment = comment;
    }
}
