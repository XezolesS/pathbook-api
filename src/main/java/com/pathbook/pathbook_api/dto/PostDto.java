package com.pathbook.pathbook_api.dto;

import com.pathbook.pathbook_api.entity.Post;

import java.time.LocalDateTime;

public class PostDto {
    private Long id;
    private UserInfoDto author;
    private String title;
    private String content;
    private Long view;
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
                dto.getCreatedAt(),
                dto.getUpdatedAt());
    }

    public PostDto(
            Long id,
            UserInfoDto author,
            String title,
            String content,
            Long view,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.view = view;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
