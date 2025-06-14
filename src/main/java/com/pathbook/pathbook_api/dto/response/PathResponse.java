package com.pathbook.pathbook_api.dto.response;

import com.pathbook.pathbook_api.entity.PostPath;

public class PathResponse {
    private String thumbnailUrl;

    public PathResponse(PostPath path) {
        this.thumbnailUrl = path.getThumbnailUrl();
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
