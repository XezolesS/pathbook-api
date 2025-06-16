// package com.pathbook.pathbook_api.service;

// import com.pathbook.pathbook_api.dto.ReportReason;
// import com.pathbook.pathbook_api.entity.Post;
// import com.pathbook.pathbook_api.entity.PostReport;
// import com.pathbook.pathbook_api.entity.User;
// import com.pathbook.pathbook_api.repository.PostReportRepository;
// import com.pathbook.pathbook_api.repository.PostRepository;
// import com.pathbook.pathbook_api.repository.UserRepository;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// @Service
// public class PostReportService {
//     @Autowired private UserRepository userRepository;

//     @Autowired private PostRepository postRepository;

//     @Autowired private PostReportRepository postReportRepository;

//     public void reportPost(
//             Long postId, String reporterId, ReportReason reason, String detailReason) {
//         Post post =
//                 postRepository
//                         .findById(postId)
//                         .orElseThrow(() -> new IllegalArgumentException("Post not found"));
//         User reporter =
//                 userRepository
//                         .findById(reporterId)
//                         .orElseThrow(() -> new IllegalArgumentException("Reporter not found"));

//         if (postReportRepository.existsByPostAndReporter(post, reporter)) {
//             throw new IllegalArgumentException("Post already reported");
//         }

//         PostReport report = new PostReport(post, reporter, reason, detailReason);
//         postReportRepository.save(report);
//     }

//     public boolean isPostHidden(Long postId) {
//         Post post =
//                 postRepository
//                         .findById(postId)
//                         .orElseThrow(() -> new IllegalArgumentException("Post not found"));

//         long reportCount = postReportRepository.countByPost(post);
//         if (reportCount >= 10) {
//             return true;
//         }
//         return false;
//     }
// }
