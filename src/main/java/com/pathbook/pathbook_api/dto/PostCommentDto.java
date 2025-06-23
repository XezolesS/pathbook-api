package com.pathbook.pathbook_api.dto;

import com.pathbook.pathbook_api.entity.PostComment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostCommentDto {
    private Long id;
    private Long parentId;
    private Long postId;
    private UserInfoDto author;
    private String content;
    private int likeCount = 0;
    private List<PostCommentDto> replies;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostCommentDto() {}

    public PostCommentDto(PostComment entity) {
        this(
                entity.getId(),
                entity.getParent() != null ? entity.getParent().getId() : null,
                entity.getPost().getId(),
                new UserInfoDto(entity.getAuthor()),
                entity.getContent(),
                entity.getLikeCount(),
                PostCommentDto.fromEntities(entity.getReplies()),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public PostCommentDto(PostCommentDto dto) {
        this(
                dto.getId(),
                dto.getParentId(),
                dto.getPostId(),
                dto.getAuthor(),
                dto.getContent(),
                dto.getLikeCount(),
                dto.getReplies(),
                dto.getCreatedAt(),
                dto.getUpdatedAt());
    }

    public PostCommentDto(
            Long id,
            Long parentId,
            Long postId,
            UserInfoDto author,
            String content,
            int likes,
            List<PostCommentDto> replies,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.parentId = parentId;
        this.postId = postId;
        this.author = author;
        this.content = content;
        this.likeCount = likes;
        this.replies = replies == null ? new ArrayList<>() : replies;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public UserInfoDto getAuthor() {
        return author;
    }

    public void setAuthor(UserInfoDto author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public List<PostCommentDto> getReplies() {
        return replies;
    }

    public void setReplies(List<PostCommentDto> replies) {
        this.replies = replies;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static List<PostCommentDto> fromEntities(List<PostComment> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }

        return entities.stream()
                .map(PostCommentDto::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void addReply(PostCommentDto reply) {
        replies.add(reply);
    }
}
