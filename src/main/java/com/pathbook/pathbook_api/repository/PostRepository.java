package com.pathbook.pathbook_api.repository;

import com.pathbook.pathbook_api.entity.Post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {}
