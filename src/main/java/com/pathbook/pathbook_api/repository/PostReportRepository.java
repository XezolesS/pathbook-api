package com.pathbook.pathbook_api.repository;

import com.pathbook.pathbook_api.entity.Post;
import com.pathbook.pathbook_api.entity.PostReport;
import com.pathbook.pathbook_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReportRepository extends JpaRepository<PostReport, Long> {
    boolean existsByPostAndReporter(Post post, User reporter);
    long countByPost(Post post);
}
