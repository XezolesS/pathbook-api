package com.pathbook.pathbook_api.entity.id;

import java.io.Serializable;
import java.util.Objects;

public class PathgroupPostItemId implements Serializable {
    private Long pathgroup;
    private Long post;

    public PathgroupPostItemId() {}

    public PathgroupPostItemId(Long pathgroup, Long post) {
        this.pathgroup = pathgroup;
        this.post = post;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof PathgroupPostItemId)) {
            return false;
        }

        PathgroupPostItemId that = (PathgroupPostItemId) object;
        return Objects.equals(pathgroup, that.pathgroup) && Objects.equals(post, that.post);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pathgroup, post);
    }

    public Long getPathgroup() {
        return pathgroup;
    }

    public void setPathgroup(Long pathgroup) {
        this.pathgroup = pathgroup;
    }

    public Long getPost() {
        return post;
    }

    public void setPost(Long post) {
        this.post = post;
    }
}
