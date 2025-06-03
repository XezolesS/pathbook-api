package com.pathbook.pathbook_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pathbook.pathbook_api.entity.UserPostLike;
import com.pathbook.pathbook_api.entity.UserPostLikeId;
import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.entity.Post;

public interface PostLikeRepository extends JpaRepository<UserPostLike, UserPostLikeId> {

    Optional<UserPostLike> findByUserAndPost(User user, Post post);

    void deleteByUserAndPost(User user, Post post);

}
