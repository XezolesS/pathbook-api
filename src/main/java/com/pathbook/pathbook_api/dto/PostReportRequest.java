package com.pathbook.pathbook_api.dto;

public record PostReportRequest(
        Long postId, String reporterId, ReportReason reason, String detailReason) {}
