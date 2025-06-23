package com.pathbook.pathbook_api.repository;

import com.pathbook.pathbook_api.entity.PostCommentLike;
import com.pathbook.pathbook_api.entity.id.PostCommentLikeId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentLikeRepository
        extends JpaRepository<PostCommentLike, PostCommentLikeId> {}
