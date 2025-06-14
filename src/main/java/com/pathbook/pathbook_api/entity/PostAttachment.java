package com.pathbook.pathbook_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "post_attachments")
public class PostAttachment {
    // region Fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "content_type", length = 64, nullable = false)
    private String contentType;

    @Column(name = "content_url", length = 2048, nullable = false)
    private String contentUrl;

    // endregion

    // region Constructors

    protected PostAttachment() {}

    public PostAttachment(Post post, String contentType, String contentUrl) {
        this.post = post;
        this.contentType = contentType;
        this.contentUrl = contentUrl;
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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    // endregion
}
