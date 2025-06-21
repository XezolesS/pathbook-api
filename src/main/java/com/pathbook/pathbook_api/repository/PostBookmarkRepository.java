package com.pathbook.pathbook_api.repository;

import com.pathbook.pathbook_api.entity.PostBookmark;
import com.pathbook.pathbook_api.entity.id.PostBookmarkId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostBookmarkRepository extends JpaRepository<PostBookmark, PostBookmarkId> {}
