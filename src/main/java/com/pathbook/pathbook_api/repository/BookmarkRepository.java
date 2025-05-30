package com.pathbook.pathbook_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pathbook.pathbook_api.entity.Bookmark;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    boolean existsByUserIdAndPostId(String userId, Long postId);

    Optional<Bookmark> findByUserIdAndPostId(String userId, Long postId);

    List<Bookmark> findAllByUserId(String userId);

    void deleteByUserIdAndPostId(String userId, Long postId);

}
