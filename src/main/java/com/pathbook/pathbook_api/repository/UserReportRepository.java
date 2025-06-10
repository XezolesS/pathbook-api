package com.pathbook.pathbook_api.repository;

import com.pathbook.pathbook_api.entity.Post;
import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.entity.UserPostReport;
import com.pathbook.pathbook_api.entity.id.UserPostReportId;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserReportRepository extends JpaRepository<UserPostReport, UserPostReportId> {
    Optional<UserPostReport> findByUserAndPost(User user, Post post);

    boolean existsByUserIdAndPostId(String userId, Integer postId);

    List<UserPostReport> findAllByUserId(String userId);

    void deleteByUserAndPost(User user, Post post);

    void deleteByUserIdAndPostId(String userId, Integer postId);
}
