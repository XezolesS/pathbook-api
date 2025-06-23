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
    private long likeCount = 0;
    private long replyCount = 0;
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
                0,
                0,
                null,
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
                dto.getReplyCount(),
                dto.getReplies(),
                dto.getCreatedAt(),
                dto.getUpdatedAt());
    }

    public PostCommentDto(Long id, Long parentId, Long postId, UserInfoDto author, String content) {
        this(id, parentId, postId, author, content, 0, 0, null, null, null);
    }

    public PostCommentDto(
            Long id,
            Long parentId,
            Long postId,
            UserInfoDto author,
            String content,
            long likeCount,
            long replyCount,
            List<PostCommentDto> replies,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.parentId = parentId;
        this.postId = postId;
        this.author = author;
        this.content = content;
        this.likeCount = likeCount;
        this.replyCount = replyCount;
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

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public long getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(long replyCount) {
        this.replyCount = replyCount;
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
