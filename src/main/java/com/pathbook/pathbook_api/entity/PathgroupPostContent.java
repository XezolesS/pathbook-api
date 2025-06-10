package com.pathbook.pathbook_api.entity;

import com.pathbook.pathbook_api.entity.id.PathgroupPostContentId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "pathgroup_post_contents")
@IdClass(PathgroupPostContentId.class)
public class PathgroupPostContent {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pathgroup_id", nullable = false)
    private Pathgroup pathgroup;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    protected PathgroupPostContent() {}

    public PathgroupPostContent(Pathgroup pathgroup, Post post) {
        this.pathgroup = pathgroup;
        this.post = post;
    }

    public Pathgroup getPathgroup() {
        return pathgroup;
    }

    public void setPathgroup(Pathgroup pathgroup) {
        this.pathgroup = pathgroup;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
