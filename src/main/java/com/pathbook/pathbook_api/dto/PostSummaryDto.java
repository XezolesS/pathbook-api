package com.pathbook.pathbook_api.dto;

import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.repository.PostRepository;

import java.time.LocalDateTime;

/**
 * 포스트 정보를 위한 Projection DTO.
 *
 * @see
 *     <p>{@link PostRepository#findAllByAuthor}
 */
public class PostSummaryDto extends PostDto {
    public PostSummaryDto(
            Long id,
            User author,
            String title,
            String content,
            Long view,
            long likeCount,
            long bookmarkCount,
            long commentCount,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        super(
                id,
                new UserInfoDto(author),
                title,
                content,
                view,
                likeCount,
                bookmarkCount,
                commentCount,
                null,
                createdAt,
                updatedAt);
    }
}
