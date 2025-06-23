package com.pathbook.pathbook_api.repository;

import com.pathbook.pathbook_api.entity.PostBookmark;
import com.pathbook.pathbook_api.entity.id.PostBookmarkId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostBookmarkRepository extends JpaRepository<PostBookmark, PostBookmarkId> {
    @Query("SELECT COUNT(c) FROM PostBookmark c WHERE c.post.id = :postId")
    public long countByPostId(Long postId);
}
