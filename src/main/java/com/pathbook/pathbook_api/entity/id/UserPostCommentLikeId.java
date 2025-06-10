package com.pathbook.pathbook_api.entity.id;

import java.io.Serializable;
import java.util.Objects;

public class UserPostCommentLikeId implements Serializable {
    private String user;
    private Integer comment;

    public UserPostCommentLikeId() {}

    public UserPostCommentLikeId(String user, Integer comment) {
        this.user = user;
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserPostCommentLikeId)) return false;
        UserPostCommentLikeId that = (UserPostCommentLikeId) o;
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

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }
}
