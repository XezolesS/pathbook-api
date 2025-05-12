package com.pathbook.pathbook_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pathbook.pathbook_api.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
