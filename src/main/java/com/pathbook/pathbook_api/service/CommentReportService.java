package com.pathbook.pathbook_api.service;

import com.pathbook.pathbook_api.entity.*;
import com.pathbook.pathbook_api.repository.CommentReportRepository;
import com.pathbook.pathbook_api.repository.CommentReportStatusRepository;
import com.pathbook.pathbook_api.repository.CommentRepository;
import com.pathbook.pathbook_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentReportService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentReportRepository commentReportRepository;

    @Autowired
    private CommentReportStatusRepository commentReportStatusRepository;

    public void reportComment(Long commentId, String reporterId, ReportReason reason, String detailReason) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(()-> new IllegalArgumentException("Comment not found"));
        User reporter = userRepository.findById(reporterId)
            .orElseThrow(()-> new IllegalArgumentException("Reporter not found"));

        if(commentReportRepository.existsByCommentAndReporter(comment, reporter)) {
            throw new IllegalArgumentException("Comment already reported");
        }

        CommentReport report = new CommentReport(comment, reporter, reason, detailReason);
        commentReportRepository.save(report);

        CommentReportStatus status = commentReportStatusRepository.findById(comment.getId())
            .orElse(new CommentReportStatus(comment.getId()));
        status.setReportCount(status.getReportCount() + 1);
        if(status.getReportCount() >= 10) {
            status.setHidden(true);
        }
    }

    public boolean isCommentHidden(Long commentId) {
        return commentReportStatusRepository.findById(commentId)
            .map(CommentReportStatus::isHidden)
            .orElse(false);
    }
}
