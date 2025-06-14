package com.pathbook.pathbook_api.dto.response;

import com.pathbook.pathbook_api.entity.PostAttachment;

public class AttachmentResponse {
    private Long attachmentId;
    private String contentType;
    private String contentUrl;

    public AttachmentResponse(PostAttachment attachment) {
        this.attachmentId = attachment.getId();
        this.contentType = attachment.getContentType();
        this.contentUrl = attachment.getContentUrl();
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public String getContentType() {
        return contentType;
    }

    public String getContentUrl() {
        return contentUrl;
    }
}
