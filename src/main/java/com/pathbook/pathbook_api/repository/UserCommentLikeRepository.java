package com.pathbook.pathbook_api.repository;

import com.pathbook.pathbook_api.entity.UserCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCommentLikeRepository extends JpaRepository<UserCommentLike, Long> {
    Optional<UserCommentLike> findByUserIdAndCommentId(String userId, Long commentId);
}
