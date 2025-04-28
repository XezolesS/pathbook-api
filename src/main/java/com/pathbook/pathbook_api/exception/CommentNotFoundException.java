package com.pathbook.pathbook_api.exception;

public class CommentNotFoundException extends RecordNotFoundException {

    private final Long commentId;

    public CommentNotFoundException(Long commentId) {
        super(String.format("Cannot find comment with id %d", commentId));

        this.commentId = commentId;
    }

    public Long getCommentId() {
        return commentId;
    }

}
