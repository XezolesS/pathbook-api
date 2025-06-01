package com.pathbook.pathbook_api.repository;

import com.pathbook.pathbook_api.entity.Comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long postId);
}
