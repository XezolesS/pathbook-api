package com.pathbook.pathbook_api.entity.id;

import java.io.Serializable;
import java.util.Objects;

public class PostAttachmentId implements Serializable {
    private Long post;
    private String file;

    public PostAttachmentId() {}

    public PostAttachmentId(Long post, String file) {
        this.post = post;
        this.file = file;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof PostAttachmentId)) {
            return false;
        }

        PostAttachmentId that = (PostAttachmentId) object;
        return Objects.equals(post, that.post) && Objects.equals(file, that.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(post, file);
    }

    public Long getPost() {
        return post;
    }

    public void setPost(Long post) {
        this.post = post;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
