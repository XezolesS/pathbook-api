package com.pathbook.pathbook_api.entity.id;

import java.util.Objects;

public class PostTagId {
    private Long post;
    private Long tag;

    public PostTagId() {}

    public PostTagId(Long post, Long tag) {
        this.post = post;
        this.tag = tag;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof PostTagId)) {
            return false;
        }

        PostTagId that = (PostTagId) object;
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
