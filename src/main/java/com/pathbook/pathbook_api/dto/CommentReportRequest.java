package com.pathbook.pathbook_api.dto;

public record CommentReportRequest(
        Long commentId, String reporterId, ReportReason reason, String detailReason) {}
