package com.pathbook.pathbook_api.entity;

import com.pathbook.pathbook_api.entity.id.PostAttachmentId;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "post_attachments")
@IdClass(PostAttachmentId.class)
public class PostAttachment {
    // region Fields

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filename", nullable = false)
    private File file;

    // endregion

    // region Constructors

    protected PostAttachment() {}

    public PostAttachment(Post post, File file) {
        this.post = post;
        this.file = file;
    }

    // endregion

    // region Getters & Setters

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    // endregion
}
