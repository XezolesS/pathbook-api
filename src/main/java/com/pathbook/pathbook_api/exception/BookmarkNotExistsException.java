package com.pathbook.pathbook_api.exception;

public class BookmarkNotExistsException extends RecordNotExistsException {

    private final String userId;
    private final Long postId;

    public BookmarkNotExistsException(String userId, Long postId) {
        super(String.format("User %s is not bookmarked post %d", userId, postId));

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
