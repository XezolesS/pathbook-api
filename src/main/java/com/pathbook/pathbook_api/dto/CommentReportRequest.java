package com.pathbook.pathbook_api.dto;

import com.pathbook.pathbook_api.entity.ReportReason;

public record CommentReportRequest(Long commentId, String reporterId, ReportReason reason, String detailReason) {
}
