package com.pathbook.pathbook_api.dto.response;

import com.pathbook.pathbook_api.entity.PostComment;

import java.time.LocalDateTime;
import java.util.List;

public class CommentResponse {
    private Long commentId;
    private Long postId;
    private UserResponse author;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int likeCount;
    private List<CommentResponse> childComment;

    public CommentResponse(PostComment comment) {
        this.commentId = comment.getId();
        this.postId = comment.getPost().getId();
        this.author = new UserResponse(comment.getAuthor());
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        this.likeCount = comment.getLikeCount();
        // this.childComment = comment.getReplies();
    }

    public Long getCommentId() {
        return commentId;
    }

    public Long getPostId() {
        return postId;
    }

    public UserResponse getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public List<CommentResponse> getChildComment() {
        return childComment;
    }
}
