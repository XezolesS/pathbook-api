package com.pathbook.pathbook_api.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "post")
public class Post {
    
    @Id
    private long id;

    @Column(name = "author_id", length = 32)
    private String authorId;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at", insertable = false)
    private Date createAt;

    protected Post() {}

    public Post(long id, String authorId, String title, String content, Date createAt) {
        this.id = id;
        this.authorId = authorId;
        this.title = title;
        this.content = content;
        this.createAt = createAt;
    }

    public long getId() {
        return this.id;
    }

    public String getAuthorId() {
        return this.authorId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public Date getCreatedAt() {
        return this.createAt;
    }

}
