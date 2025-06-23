package com.pathbook.pathbook_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import org.locationtech.jts.geom.LineString;

@Entity
@Table(name = "post_paths")
public class PostPath {
    // region Fields

    @Id private Long id;

    @Column(name = "path_points", nullable = false, columnDefinition = "LINESTRING")
    private LineString pathPoints;

    @OneToOne
    @JoinColumn(name = "thumbnail_filename", referencedColumnName = "filename", nullable = false)
    private File thumbnailFile;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Post post;

    // endregion

    // region Constructors

    protected PostPath() {}

    public PostPath(Post post, LineString pathPoints, File thumbnailFile) {
        this.post = post;
        this.pathPoints = pathPoints;
        this.thumbnailFile = thumbnailFile;
    }

    // endregion

    // region Getters & Setters

    public Long getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public LineString getPathPoints() {
        return pathPoints;
    }

    public void setPathPoints(LineString pathPoints) {
        this.pathPoints = pathPoints;
    }

    public File getThumbnailFile() {
        return thumbnailFile;
    }

    public void setThumbnailFile(File thumbnailFile) {
        this.thumbnailFile = thumbnailFile;
    }

    // endregion
}
