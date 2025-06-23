package com.pathbook.pathbook_api.dto;

import com.pathbook.pathbook_api.entity.Post;
import com.pathbook.pathbook_api.entity.PostAttachment;
import com.pathbook.pathbook_api.entity.PostComment;
import com.pathbook.pathbook_api.entity.PostPath;
import com.pathbook.pathbook_api.entity.PostTag;
import com.pathbook.pathbook_api.entity.Tag;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PostDto {
    // region Fields

    private Long id;
    private UserInfoDto author;
    private String title;
    private String content;
    private PostPathDto path;
    private List<FileMetaDto> attachments;
    private Set<String> tags;
    private Long view = 0L;
    private long likeCount = 0;
    private long bookmarkCount = 0;
    private long commentCount = 0;
    private List<PostCommentDto> comments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // endregion

    // region Constructors

    public PostDto() {}

    public PostDto(Post entity) {
        this(
                entity.getId(),
                new UserInfoDto(entity.getAuthor()),
                entity.getTitle(),
                entity.getContent(),
                null,
                null,
                null,
                entity.getView(),
                0,
                0,
                0,
                null,
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public PostDto(PostDto dto) {
        this(
                dto.id,
                dto.author,
                dto.title,
                dto.content,
                dto.path,
                dto.attachments,
                dto.tags,
                dto.view,
                dto.likeCount,
                dto.bookmarkCount,
                dto.commentCount,
                dto.comments,
                dto.createdAt,
                dto.updatedAt);
    }

    public PostDto(Long id, UserInfoDto author, String title, String content, Long view) {
        this(id, author, title, content, null, null, null, view, 0, 0, 0, null, null, null);
    }

    protected PostDto(
            Long id,
            UserInfoDto author,
            String title,
            String content,
            PostPathDto path,
            List<FileMetaDto> attachments,
            Set<String> tags,
            Long view,
            long likeCount,
            long bookmarkCount,
            long commentCount,
            List<PostCommentDto> comments,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.path = path;
        this.attachments = attachments;
        this.tags = tags;
        this.view = view;
        this.likeCount = likeCount;
        this.bookmarkCount = bookmarkCount;
        this.commentCount = commentCount;
        this.comments = comments == null ? new ArrayList<>() : comments;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // endregion

    // region Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserInfoDto getAuthor() {
        return author;
    }

    public void setAuthor(UserInfoDto author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PostPathDto getPath() {
        return path;
    }

    public void setPath(PostPathDto path) {
        this.path = path;
    }

    public List<FileMetaDto> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<FileMetaDto> attachments) {
        this.attachments = attachments;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Long getView() {
        return view;
    }

    public void setView(Long view) {
        this.view = view;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public long getBookmarkCount() {
        return bookmarkCount;
    }

    public void setBookmarkCount(long bookmarkCount) {
        this.bookmarkCount = bookmarkCount;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public List<PostCommentDto> getComments() {
        return comments;
    }

    public void setComments(List<PostCommentDto> comments) {
        this.comments = comments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void addComment(PostCommentDto comment) {
        comments.add(comment);
    }

    // endregion

    // region Factory Methods

    public PostDto withPathEntity(PostPath path) {
        return withPath(new PostPathDto(path));
    }

    public PostDto withAttachmentEntities(List<PostAttachment> attachments) {
        return withAttachments(
                FileMetaDto.fromEntities(
                        attachments.stream().map(PostAttachment::getFile).toList()));
    }

    public PostDto withTagEntities(Set<PostTag> entities) {
        return withTags(
                entities.stream()
                        .map(PostTag::getTag)
                        .map(Tag::getName)
                        .collect(Collectors.toSet()));
    }

    public PostDto withCommentEntities(List<PostComment> comments) {
        return withComments(PostCommentDto.fromEntities(comments));
    }

    public PostDto withPath(PostPathDto path) {
        setPath(path);
        return this;
    }

    public PostDto withAttachments(List<FileMetaDto> attachments) {
        setAttachments(attachments);
        return this;
    }

    public PostDto withTags(Set<String> tags) {
        setTags(tags);
        return this;
    }

    public PostDto withComments(List<PostCommentDto> comments) {
        setComments(comments);
        return this;
    }

    // endregion
}
