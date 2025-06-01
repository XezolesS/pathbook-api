package com.pathbook.pathbook_api.dto;

import com.pathbook.pathbook_api.entity.ReportReason;

public record PostReportRequest(Long postId, String reporterId, ReportReason reason, String detailReason) {
}
