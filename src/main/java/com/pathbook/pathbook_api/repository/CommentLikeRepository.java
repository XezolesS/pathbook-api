package com.pathbook.pathbook_api.repository;

import com.pathbook.pathbook_api.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLikeRepository> findByUserIdAndCommentId(String userId, Long commentId);

    void deleteByUserIdAndCommentId(String userId, Long commentId);

}
