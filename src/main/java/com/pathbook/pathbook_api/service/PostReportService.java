package com.pathbook.pathbook_api.service;

import com.pathbook.pathbook_api.dto.PostReportRequest;
import com.pathbook.pathbook_api.entity.*;
import com.pathbook.pathbook_api.repository.PostReportRepository;
import com.pathbook.pathbook_api.repository.PostReportStatusRepository;
import com.pathbook.pathbook_api.repository.PostRepository;
import com.pathbook.pathbook_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostReportService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostReportRepository postReportRepository;

    @Autowired
    private PostReportStatusRepository postReportStatusRepository;

    public void reportPost(Long postId, String reporterId, ReportReason reason, String detailReason) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        User reporter = userRepository.findById(reporterId)
                .orElseThrow(() -> new IllegalArgumentException("Reporter not found"));

        if(postReportRepository.existsByPostAndReporter(post, reporter)) {
            throw new IllegalArgumentException("Post already exists");
        }

        PostReport report = new PostReport(post, reporter, reason, detailReason);
        postReportRepository.save(report);

        PostReportStatus status = postReportStatusRepository.findById(post.getId())
                .orElse(new PostReportStatus(post.getId()));
        status.setReportCount(status.getReportCount()+1);
        if(status.getReportCount() >= 10) {
            status.setHidden(true);
        }

        postReportStatusRepository.save(status);
    }

    public boolean isPostHidden(Long postId) {
        return postReportStatusRepository.findById(postId)
                .map(PostReportStatus::isHidden)
                .orElse(false);
    }
}
