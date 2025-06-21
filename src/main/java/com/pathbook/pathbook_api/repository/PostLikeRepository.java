package com.pathbook.pathbook_api.repository;

import com.pathbook.pathbook_api.entity.PostLike;
import com.pathbook.pathbook_api.entity.id.PostLikeId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, PostLikeId> {}
