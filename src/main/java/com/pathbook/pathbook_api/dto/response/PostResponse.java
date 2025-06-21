package com.pathbook.pathbook_api.dto.response;

import com.pathbook.pathbook_api.dto.PostDto;
import com.pathbook.pathbook_api.entity.Post;

public class PostResponse extends PostDto {
    public PostResponse() {
        super();
    }

    public PostResponse(Post entity) {
        super(entity);
    }

    public PostResponse(PostDto dto) {
        super(dto);
    }
}
