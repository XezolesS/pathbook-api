package com.pathbook.pathbook_api.exception;

public class BookmarkAlreadyExistsException extends RecordAlreadyExistsException {
    private final String userId;
    private final Long postId;

    public BookmarkAlreadyExistsException(String userId, Long postId) {
        super(String.format("User %s already bookmarked post %d", userId, postId));

        this.userId = userId;
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public Long getPostId() {
        return postId;
    }
}
