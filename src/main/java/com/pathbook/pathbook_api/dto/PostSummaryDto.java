package com.pathbook.pathbook_api.dto;

import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.repository.PostRepository;

import java.time.LocalDateTime;

/**
 * 포스트 정보를 위한 Projection DTO.
 *
 * @see
 *     <p>{@link PostRepository#findAllByAuthor}
 */
public class PostSummaryDto {
    private Long id;
    private UserInfoDto author;
    private String title;
    private String content;
    private Long view = 0L;
    private long likeCount = 0;
    private long bookmarkCount = 0;
    private long commentCount = 0;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostSummaryDto() {}

    public PostSummaryDto(PostSummaryDto dto) {
        this(
                dto.id,
                dto.author,
                dto.title,
                dto.content,
                dto.view,
                dto.likeCount,
                dto.bookmarkCount,
                dto.commentCount,
                dto.createdAt,
                dto.updatedAt);
    }

    public PostSummaryDto(
            Long id,
            User author,
            String title,
            String content,
            Long view,
            long likeCount,
            long bookmarkCount,
            long commentCount,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this(
                id,
                new UserInfoDto(author),
                title,
                content,
                view,
                likeCount,
                bookmarkCount,
                commentCount,
                createdAt,
                updatedAt);
    }

    public PostSummaryDto(
            Long id,
            UserInfoDto author,
            String title,
            String content,
            Long view,
            long likeCount,
            long bookmarkCount,
            long commentCount,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.author = new UserInfoDto(author);
        this.title = title;
        this.content = content;
        this.view = view;
        this.likeCount = likeCount;
        this.bookmarkCount = bookmarkCount;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public UserInfoDto getAuthor() {
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

    public long getLikeCount() {
        return likeCount;
    }

    public long getBookmarkCount() {
        return bookmarkCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
