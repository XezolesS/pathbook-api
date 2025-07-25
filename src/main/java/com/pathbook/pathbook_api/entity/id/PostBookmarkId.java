package com.pathbook.pathbook_api.entity.id;

import java.io.Serializable;
import java.util.Objects;

public class PostBookmarkId implements Serializable {
    private String user;
    private Long post;

    public PostBookmarkId() {}

    public PostBookmarkId(String user, Long post) {
        this.user = user;
        this.post = post;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof PostBookmarkId)) {
            return false;
        }

        PostBookmarkId that = (PostBookmarkId) object;
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
