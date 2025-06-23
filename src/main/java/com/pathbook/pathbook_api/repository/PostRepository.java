package com.pathbook.pathbook_api.repository;

import com.pathbook.pathbook_api.dto.PostDto;
import com.pathbook.pathbook_api.entity.Post;
import com.pathbook.pathbook_api.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByAuthor(User author);

    @Query(
            """
            SELECT new com.pathbook.pathbook_api.dto.PostSummaryDto(
                p.id, p.author, p.title, p.content, p.view,
                COUNT(DISTINCT l.id),
                COUNT(DISTINCT b.id),
                COUNT(DISTINCT c.id),
                p.createdAt,
                p.updatedAt
            )
            FROM Post p
            LEFT JOIN PostLike l ON l.post = p
            LEFT JOIN PostBookmark b ON b.post = p
            LEFT JOIN PostComment c ON c.post = p
            GROUP BY p.id
            """)
    List<PostDto> findAllWithCounts();
}
