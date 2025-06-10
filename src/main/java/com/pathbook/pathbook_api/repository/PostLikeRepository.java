package com.pathbook.pathbook_api.repository;

import com.pathbook.pathbook_api.entity.Post;
import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.entity.UserPostLike;
import com.pathbook.pathbook_api.entity.id.UserPostLikeId;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<UserPostLike, UserPostLikeId> {

    Optional<UserPostLike> findByUserAndPost(User user, Post post);

    void deleteByUserAndPost(User user, Post post);

    void deleteByUserIdAndPostId(String userId, Integer postId);
}
