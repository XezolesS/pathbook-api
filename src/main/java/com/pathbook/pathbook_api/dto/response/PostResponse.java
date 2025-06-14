package com.pathbook.pathbook_api.dto.response;

import com.pathbook.pathbook_api.entity.Post;
import com.pathbook.pathbook_api.entity.PostLike;
import com.pathbook.pathbook_api.entity.User;

import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.List;

public class PostResponse {
    private Long id;
    private String authorId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<PostLikeResponse> likes;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.authorId = post.getAuthorId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.likes = mapLikes(post.getLikes());
    }

    private List<PostLikeResponse> mapLikes(List<PostLike> postLikes) {
        return postLikes.stream()
                .map(
                        likes ->
                                new PostLikeResponse(
                                        Hibernate.unproxy(likes.getUser(), User.class),
                                        likes.getCreatedAt()))
                .toList();
    }

    public Long getId() {
        return id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<PostLikeResponse> getLikes() {
        return likes;
    }
}
