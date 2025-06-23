package com.pathbook.pathbook_api.entity;

import com.pathbook.pathbook_api.entity.id.PostTagId;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "post_tags")
@IdClass(PostTagId.class)
public class PostTag {
    // region Fields

    @ManyToOne
    @Id
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @Id
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    // endregion

    // region Constructors

    protected PostTag() {}

    public PostTag(Post post, Tag tag) {
        this.post = post;
        this.tag = tag;
    }

    // endregion

    // region Getters & Setters

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    // endregion
}
