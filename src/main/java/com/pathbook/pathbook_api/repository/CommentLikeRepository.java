package com.pathbook.pathbook_api.repository;

import com.pathbook.pathbook_api.entity.UserPostCommentLike;
import com.pathbook.pathbook_api.entity.UserPostCommentLikeId;
import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<UserPostCommentLike, UserPostCommentLikeId> {

    Optional<UserPostCommentLike> findByUserAndComment(User user, PostComment comment);

    void deleteByUserAndComment(User user, PostComment comment);

}
