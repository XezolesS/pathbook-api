package com.pathbook.pathbook_api.dto.response;

import com.pathbook.pathbook_api.dto.PostCommentDto;
import com.pathbook.pathbook_api.entity.PostComment;

public class PostCommentResponse extends PostCommentDto {
    public PostCommentResponse() {}

    public PostCommentResponse(PostComment entity) {
        super(entity);
    }

    public PostCommentResponse(PostCommentDto dto) {
        super(dto);
    }
}
