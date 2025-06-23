package com.pathbook.pathbook_api.exception;

public class PostCommentNotFoundException extends RecordNotFoundException {
    private final Long commentId;

    public PostCommentNotFoundException(Long commentId) {
        super(String.format("Comment not found with ID: %d", commentId));

        this.commentId = commentId;
    }

    public Long getCommentId() {
        return commentId;
    }
}
