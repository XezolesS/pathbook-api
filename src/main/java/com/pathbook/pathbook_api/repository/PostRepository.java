package com.pathbook.pathbook_api.repository;

import com.pathbook.pathbook_api.entity.Post;
import com.pathbook.pathbook_api.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByAuthor(User author);
}
