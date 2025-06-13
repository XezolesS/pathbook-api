package com.pathbook.pathbook_api.entity.id;

import java.io.Serializable;
import java.util.Objects;

public class PostCommentLikeId implements Serializable {
    private String user;
    private Long comment;

    public PostCommentLikeId() {}

    public PostCommentLikeId(String user, Long comment) {
        this.user = user;
        this.comment = comment;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof PostCommentLikeId)) {
            return false;
        }

        PostCommentLikeId that = (PostCommentLikeId) object;
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
