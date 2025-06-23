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
    private Long view = 0L;
    private int likeCount = 0;
    private int bookmarkCount = 0;
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
                entity.getView(),
                entity.getLikeCount(),
                entity.getBookmarkCount(),
                PostCommentDto.fromEntities(entity.getComments()),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public PostDto(PostDto dto) {
        this(
                dto.getId(),
                dto.getAuthor(),
                dto.getTitle(),
                dto.getContent(),
                dto.getView(),
                dto.getLikeCount(),
                dto.getBookmarkCount(),
                dto.getComments(),
                dto.getCreatedAt(),
                dto.getUpdatedAt());
    }

    public PostDto(
            Long id,
            UserInfoDto author,
            String title,
            String content,
            Long view,
            int likes,
            int bookmarks,
            List<PostCommentDto> comments,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.view = view;
        this.likeCount = likes;
        this.bookmarkCount = bookmarks;
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

    public Long getView() {
        return view;
    }

    public void setView(Long view) {
        this.view = view;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getBookmarkCount() {
        return bookmarkCount;
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
