package com.pathbook.pathbook_api.repository;

import com.pathbook.pathbook_api.entity.PostLike;
import com.pathbook.pathbook_api.entity.id.PostLikeId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {
    @Query("SELECT COUNT(c) FROM PostLike c WHERE c.post.id = :postId")
    public long countByPostId(Long postId);
}
