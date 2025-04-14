package com.pathbook.pathbook_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pathbook.pathbook_api.entity.Bookmark;
import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.entity.Post;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByUserAndPost(User user, Post post);
    List<Bookmark> findAllByUser(User user);
    void deleteByUserAndPost(User user, Post post);
}
