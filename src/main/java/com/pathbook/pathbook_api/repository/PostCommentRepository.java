package com.pathbook.pathbook_api.repository;

import com.pathbook.pathbook_api.entity.PostComment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    public List<PostComment> findAllByPostId(Long postId);

    @Query("SELECT COUNT(c) FROM PostComment c WHERE c.post.id = :postId")
    public long countByPostId(Long postId);
}
