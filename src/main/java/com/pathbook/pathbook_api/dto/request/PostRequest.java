package com.pathbook.pathbook_api.dto.request;

import com.pathbook.pathbook_api.dto.PostDto;
import com.pathbook.pathbook_api.entity.Post;

public class PostRequest extends PostDto {
    public PostRequest() {
        super();
    }

    public PostRequest(Post entity) {
        super(entity);
    }

    public PostRequest(PostDto dto) {
        super(dto);
    }
}
