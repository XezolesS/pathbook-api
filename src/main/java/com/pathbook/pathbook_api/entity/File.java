package com.pathbook.pathbook_api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pathbook.pathbook_api.dto.FileMeta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "files")
public class File {
    // region Fields

    @Id
    @Column(name = "filename", nullable = false)
    private String filename;

    @Column(name = "originalFilename", length = 1024, nullable = false)
    private String originalFilename;

    @Column(name = "content_type", length = 128, nullable = false)
    private String contentType;

    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne @JsonBackReference private User owner;

    // endregion

    // region Constructors

    protected File() {}

    public File(String filename, String originalFilename, String contentType, Long size) {
        this(filename, originalFilename, contentType, size, null);
    }

    public File(
            String filename, String originalFilename, String contentType, Long size, User owner) {
        this.filename = filename;
        this.originalFilename = originalFilename;
        this.contentType = contentType;
        this.size = size;
        this.owner = owner;
    }

    // endregion

    // region Getters & Setters

    public String getFilename() {
        return filename;
    }

    public void setFilename(String id) {
        this.filename = id;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getOwnerId() {
        if (owner == null) {
            return null;
        }

        return owner.getId();
    }

    // endregion

    // region Events

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // endregion
}
