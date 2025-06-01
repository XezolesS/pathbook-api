package com.pathbook.pathbook_api.repository;

import com.pathbook.pathbook_api.entity.Comment;
import com.pathbook.pathbook_api.entity.CommentReport;
import com.pathbook.pathbook_api.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {
    boolean existsByCommentAndReporter(Comment comment, User reporter);

    long countByComment(Comment comment);
}
