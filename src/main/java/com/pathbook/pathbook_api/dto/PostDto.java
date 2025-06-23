package com.pathbook.pathbook_api.dto;

import com.pathbook.pathbook_api.entity.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostDto {
    private Long id;
    private UserInfoDto author;
    private String title;
    private String content;
    private PostPathDto path;
    private Long view = 0L;
    private long likeCount = 0;
    private long bookmarkCount = 0;
    private long commentCount = 0;
    private List<PostCommentDto> comments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostDto() {}

    public PostDto(Post entity) {
        this(
                entity.getId(),
                new UserInfoDto(entity.getAuthor()),
                entity.getTitle(),
                entity.getContent(),
                new PostPathDto(entity.getPath()),
                entity.getView(),
                0,
                0,
                0,
                null,
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public PostDto(PostDto dto) {
        this(
                dto.id,
                dto.author,
                dto.title,
                dto.content,
                dto.path,
                dto.view,
                dto.likeCount,
                dto.bookmarkCount,
                dto.commentCount,
                dto.comments,
                dto.createdAt,
                dto.updatedAt);
    }

    public PostDto(Long id, UserInfoDto author, String title, String content, Long view) {
        this(id, author, title, content, null, view, 0, 0, 0, null, null, null);
    }

    public PostDto(
            Long id,
            UserInfoDto author,
            String title,
            String content,
            PostPathDto path,
            Long view) {
        this(id, author, title, content, path, view, 0, 0, 0, null, null, null);
    }

    public PostDto(
            Long id,
            UserInfoDto author,
            String title,
            String content,
            PostPathDto path,
            Long view,
            long likeCount,
            long bookmarkCount,
            long commentCount,
            List<PostCommentDto> comments,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.path = path;
        this.view = view;
        this.likeCount = likeCount;
        this.bookmarkCount = bookmarkCount;
        this.commentCount = commentCount;
        this.comments = comments == null ? new ArrayList<>() : comments;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserInfoDto getAuthor() {
        return author;
    }

    public void setAuthor(UserInfoDto author) {
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

    public PostPathDto getPath() {
        return path;
    }

    public void setPath(PostPathDto path) {
        this.path = path;
    }

    public Long getView() {
        return view;
    }

    public void setView(Long view) {
        this.view = view;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public long getBookmarkCount() {
        return bookmarkCount;
    }

    public void setBookmarkCount(long bookmarkCount) {
        this.bookmarkCount = bookmarkCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public List<PostCommentDto> getComments() {
        return comments;
    }

    public void setComments(List<PostCommentDto> comments) {
        this.comments = comments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void addComment(PostCommentDto comment) {
        comments.add(comment);
    }
}
