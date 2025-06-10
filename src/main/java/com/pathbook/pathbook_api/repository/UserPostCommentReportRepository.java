package com.pathbook.pathbook_api.repository;

import com.pathbook.pathbook_api.entity.PostComment;
import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.entity.UserPostCommentReport;
import com.pathbook.pathbook_api.entity.id.UserPostCommentReportId;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserPostCommentReportRepository
        extends JpaRepository<UserPostCommentReport, UserPostCommentReportId> {
    Optional<UserPostCommentReport> findByUserAndComment(User user, PostComment comment);

    boolean existsByUserIdAndCommentId(String userId, Integer commentId);

    List<UserPostCommentReport> findAllByUserId(String userId);

    void deleteByUserAndComment(User user, PostComment comment);

    void deleteByUserIdAndCommentId(String userId, Integer commentId);
}
