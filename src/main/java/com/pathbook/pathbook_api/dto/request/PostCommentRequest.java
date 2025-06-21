package com.pathbook.pathbook_api.dto.request;

import com.pathbook.pathbook_api.dto.PostCommentDto;
import com.pathbook.pathbook_api.entity.PostComment;

public class PostCommentRequest extends PostCommentDto {
    public PostCommentRequest() {}

    public PostCommentRequest(PostComment entity) {
        super(entity);
    }

    public PostCommentRequest(PostCommentDto dto) {
        super(dto);
    }
}
