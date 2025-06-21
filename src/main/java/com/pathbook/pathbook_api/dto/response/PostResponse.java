package com.pathbook.pathbook_api.dto.response;

import com.pathbook.pathbook_api.entity.Post;
import com.pathbook.pathbook_api.entity.PostAttachment;
import com.pathbook.pathbook_api.entity.PostTag;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class PostResponse {
    private Long id;
    private UserResponse author;
    private String title;
    private String content;
    private Long view;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private PathResponse path;
    private List<AttachmentResponse> attachments;
    private List<CommentResponse> rootComments;
    private int likeCount;
    private int bookmarkCount;
    private HashSet<String> tags;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.author = UserResponse.fromUser(post.getAuthor());
        this.title = post.getTitle();
        this.content = post.getContent();
        this.view = post.getView();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.path = new PathResponse(post.getPath());
        this.attachments = mapAttachments(post.getAttachments());
        // this.rootComments =
        this.likeCount = post.getLikeCount();
        this.bookmarkCount = post.getBookmarkCount();
        this.tags = mapTags(post.getTags());
    }

    public Long getId() {
        return id;
    }

    public UserResponse getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getView() {
        return view;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public PathResponse getPath() {
        return path;
    }

    public List<AttachmentResponse> getAttachments() {
        return attachments;
    }

    public List<CommentResponse> getRootComments() {
        return rootComments;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getBookmarkCount() {
        return bookmarkCount;
    }

    public HashSet<String> getTags() {
        return tags;
    }

    private List<AttachmentResponse> mapAttachments(List<PostAttachment> attachments) {
        return attachments.stream().map(AttachmentResponse::new).toList();
    }

    private HashSet<String> mapTags(HashSet<PostTag> tags) {
        return tags.stream()
                .map(tag -> tag.getTag().getName())
                .collect(Collectors.toCollection(HashSet::new));
    }
}
