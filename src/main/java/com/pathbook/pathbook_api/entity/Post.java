package com.pathbook.pathbook_api.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post {
    // region Fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Column(name = "title", columnDefinition = "TINYTEXT", nullable = false)
    private String title;

    @Lob
    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "view", nullable = false)
    private Long view = 0L;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    private PostPath path;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<PostComment> comments = new ArrayList<>();

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<PostLike> likes = new ArrayList<>();

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<PostBookmark> bookmarks = new ArrayList<>();

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<PostReport> reports = new ArrayList<>();

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<PostAttachment> attachments = new ArrayList<>();

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private Set<PostTag> tags = new HashSet<>();

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<PathgroupPostItem> pathgroupItems = new ArrayList<>();

    // endregion

    // region Constructors

    protected Post() {}

    public Post(User author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }

    // endregion

    // region Getters & Setters

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getView() {
        return view;
    }

    public void setView(Long view) {
        this.view = view;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public PostPath getPath() {
        return path;
    }

    public void setPath(PostPath path) {
        if (this.path != null) {
            this.path.setPost(null);
        }

        this.path = path;
        if (path != null) {
            path.setPost(this);
        }
    }

    public List<PostComment> getComments() {
        return comments;
    }

    public List<PostLike> getLikes() {
        return likes;
    }

    public List<PostBookmark> getBookmarks() {
        return bookmarks;
    }

    public List<PostReport> getReports() {
        return reports;
    }

    public List<PostAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<PostAttachment> attachments) {
        this.attachments.clear();

        if (attachments != null) {
            for (PostAttachment attachment : attachments) {
                attachment.setPost(this);
            }

            this.attachments.addAll(attachments);
        }
    }

    public Set<PostTag> getTags() {
        return tags;
    }

    public void setTags(Set<PostTag> tags) {
        this.tags.clear();

        if (tags != null) {
            for (PostTag tag : tags) {
                tag.setPost(this);
            }

            this.tags.addAll(tags);
        }
    }

    public List<PathgroupPostItem> getPathgroupItems() {
        return pathgroupItems;
    }

    // endregion

    // region Events

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // endregion

    // region Helper Methods

    public void increaseView() {
        view++;
    }

    public boolean hasPath() {
        return path != null;
    }

    public int getCommentCount() {
        return comments.size();
    }

    public int getLikeCount() {
        return likes.size();
    }

    public int getBookmarkCount() {
        return bookmarks.size();
    }

    // endregion
}
