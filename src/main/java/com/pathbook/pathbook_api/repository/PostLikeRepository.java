package com.pathbook.pathbook_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pathbook.pathbook_api.entity.PostLike;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findByUserIdAndPostId(String userId, Long postId);

    void deleteByUserIdAndPostId(String userId, Long postId);

}
