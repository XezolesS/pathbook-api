package com.pathbook.pathbook_api.entity;

import java.io.Serializable;
import java.util.Objects;

public class UserPostBookmarkId implements Serializable {
    private String user;
    private Integer post;

    public UserPostBookmarkId() {}

    public UserPostBookmarkId(String user, Integer post) {
        this.user = user;
        this.post = post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserPostBookmarkId)) return false;
        UserPostBookmarkId that = (UserPostBookmarkId) o;
        return Objects.equals(user, that.user) &&
               Objects.equals(post, that.post);
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

    public Integer getPost() {
        return post;
    }

    public void setPost(Integer post) {
        this.post = post;
    }
}