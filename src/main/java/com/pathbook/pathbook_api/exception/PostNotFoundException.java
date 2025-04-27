package com.pathbook.pathbook_api.exception;

public class PostNotFoundException extends RecordNotFoundException {
    
    private final Long postId;

    public PostNotFoundException(Long postId) {
        super(String.format("Post %d not found", postId));

        this.postId = postId;
    }

    public Long getPostId() {
        return postId;
    }

}
