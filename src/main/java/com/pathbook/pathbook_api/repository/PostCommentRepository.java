package com.pathbook.pathbook_api.repository;

import com.pathbook.pathbook_api.entity.PostComment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    public List<PostComment> findAllByPostId(Long postId);
}
