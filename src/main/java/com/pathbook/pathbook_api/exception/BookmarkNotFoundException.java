package com.pathbook.pathbook_api.exception;

public class BookmarkNotFoundException extends RecordNotFoundException {

    private final String userId;
    private final Long postId;

    public BookmarkNotFoundException(String userId, Long postId) {
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
