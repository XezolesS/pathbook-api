package com.pathbook.pathbook_api.entity.id;

import java.util.Objects;

public class PostHasTagId {
    private Long post;
    private Long tag;

    public PostHasTagId() {}

    public PostHasTagId(Long post, Long tag) {
        this.post = post;
        this.tag = tag;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof PostHasTagId)) {
            return false;
        }

        PostHasTagId that = (PostHasTagId) object;
        return Objects.equals(post, that.post) && Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(post, tag);
    }

    public Long getPost() {
        return post;
    }

    public void setPost(Long post) {
        this.post = post;
    }

    public Long getTag() {
        return tag;
    }

    public void setTag(Long tag) {
        this.tag = tag;
    }
}
