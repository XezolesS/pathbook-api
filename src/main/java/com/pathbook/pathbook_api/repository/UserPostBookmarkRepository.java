package com.pathbook.pathbook_api.repository;

import com.pathbook.pathbook_api.entity.UserPostBookmark;
import com.pathbook.pathbook_api.entity.UserPostBookmarkId;
import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserPostBookmarkRepository extends JpaRepository<UserPostBookmark, UserPostBookmarkId> {
    Optional<UserPostBookmark> findByUserAndPost(User user, Post post);
    boolean existsByUserIdAndPostId(String userId, Integer postId);
    List<UserPostBookmark> findAllByUserId(String userId);
    void deleteByUserAndPost(User user, Post post);
    void deleteByUserIdAndPostId(String userId, Integer postId);
}
