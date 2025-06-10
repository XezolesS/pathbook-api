package com.pathbook.pathbook_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "post_paths")
public class PostPath {
    @OneToOne
    @MapsId
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "path_points", nullable = false, columnDefinition = "LINESTRING")
    private String pathPoints;

    @Column(name = "thumbnail_url", length = 2048)
    private String thumbnailUrl;

    protected PostPath() {}

    public PostPath(Post post, String pathPoints, String thumbnailUrl) {
        this.post = post;
        this.pathPoints = pathPoints;
        this.thumbnailUrl = thumbnailUrl;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getPathPoints() {
        return pathPoints;
    }

    public void setPathPoints(String pathPoints) {
        this.pathPoints = pathPoints;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
