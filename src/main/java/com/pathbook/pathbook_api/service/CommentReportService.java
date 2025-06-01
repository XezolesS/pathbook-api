package com.pathbook.pathbook_api.service;

import com.pathbook.pathbook_api.entity.Comment;
import com.pathbook.pathbook_api.entity.CommentReport;
import com.pathbook.pathbook_api.entity.ReportReason;
import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.repository.CommentReportRepository;
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

    public void reportComment(Long commentId, String reporterId, ReportReason reason, String detailReason) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        User reporter = userRepository.findById(reporterId)
                .orElseThrow(() -> new IllegalArgumentException("Reporter not found"));

        if (commentReportRepository.existsByCommentAndReporter(comment, reporter)) {
            throw new IllegalArgumentException("Comment already reported");
        }

        CommentReport report = new CommentReport(comment, reporter, reason, detailReason);
        commentReportRepository.save(report);
    }

    public boolean isCommentHidden(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        long reportCount = commentReportRepository.countByComment(comment);
        if (reportCount >= 10) {
            return true;
        }
        return false;
    }
}
