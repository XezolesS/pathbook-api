package com.pathbook.pathbook_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "post_paths")
public class PostPath {

    @Id
    @Column(name = "post_id", nullable = false)
    private Integer postId;

    @Column(name = "path_points", nullable = false, columnDefinition = "LINESTRING")
    private String pathPoints;

    protected PostPath() {}

    public PostPath(Integer postId, String pathPoints) {
        this.postId = postId;
        this.pathPoints = pathPoints;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getPathPoints() {
        return pathPoints;
    }

    public void setPathPoints(String pathPoints) {
        this.pathPoints = pathPoints;
    }
}
