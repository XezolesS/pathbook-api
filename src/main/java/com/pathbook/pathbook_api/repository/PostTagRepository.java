package com.pathbook.pathbook_api.repository;

import com.pathbook.pathbook_api.entity.PostTag;
import com.pathbook.pathbook_api.entity.id.PostTagId;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostTagRepository extends JpaRepository<PostTag, PostTagId> {
    List<PostTag> findByPostId(Long postId);
}
