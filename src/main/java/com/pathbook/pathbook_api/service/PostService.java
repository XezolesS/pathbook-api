package com.pathbook.pathbook_api.service;

import com.pathbook.pathbook_api.dto.FetchOption;
import com.pathbook.pathbook_api.dto.FileMetaDto;
import com.pathbook.pathbook_api.dto.PostCommentDto;
import com.pathbook.pathbook_api.dto.PostDto;
import com.pathbook.pathbook_api.dto.PostPathPointDto;
import com.pathbook.pathbook_api.dto.PostSortOption;
import com.pathbook.pathbook_api.dto.PostSummaryDto;
import com.pathbook.pathbook_api.entity.File;
import com.pathbook.pathbook_api.entity.Post;
import com.pathbook.pathbook_api.entity.PostAttachment;
import com.pathbook.pathbook_api.entity.PostBookmark;
import com.pathbook.pathbook_api.entity.PostComment;
import com.pathbook.pathbook_api.entity.PostLike;
import com.pathbook.pathbook_api.entity.PostPath;
import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.entity.id.PostBookmarkId;
import com.pathbook.pathbook_api.entity.id.PostLikeId;
import com.pathbook.pathbook_api.exception.PostNotFoundException;
import com.pathbook.pathbook_api.exception.StorageFileNotFoundException;
import com.pathbook.pathbook_api.exception.UnauthorizedAccessException;
import com.pathbook.pathbook_api.repository.FileRepository;
import com.pathbook.pathbook_api.repository.PostAttachmentRepository;
import com.pathbook.pathbook_api.repository.PostBookmarkRepository;
import com.pathbook.pathbook_api.repository.PostCommentRepository;
import com.pathbook.pathbook_api.repository.PostLikeRepository;
import com.pathbook.pathbook_api.repository.PostRepository;
import com.pathbook.pathbook_api.storage.StorageService;

import org.locationtech.jts.geom.LineString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired private UserService userService;

    @Autowired private StorageService storageService;

    @Autowired private PostRepository postRepository;

    @Autowired private PostCommentRepository postCommentRepository;

    @Autowired private PostLikeRepository postLikeRepository;

    @Autowired private PostBookmarkRepository postBookmarkRepository;

    @Autowired private PostAttachmentRepository postAttachmentRepository;

    @Autowired private FileRepository fileRepository;

    /**
     * 포스트 ID로부터 포스트 엔티티를 불러옵니다.
     *
     * @param postId
     * @return {@link Post}
     */
    public Post fromPostId(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
    }

    /**
     * 포스트 ID로부터 포스트 존재 여부를 확인합니다.
     *
     * @param postId
     * @return
     */
    public boolean existsByPostId(Long postId) {
        return postRepository.existsById(postId);
    }

    /**
     * 포스트의 요약 정보를 페이지 단위로 불러옵니다.
     *
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @param sortOption {@link PostSortOption}
     * @return
     */
    @Transactional(readOnly = true)
    public Page<PostSummaryDto> getPostSummaries(int page, int size, PostSortOption sortOption) {
        // 더 고차원적인 정렬 옵션은 Criteria API, QueryDSL 등 외부 라이브러리 사용

        Sort sort;
        switch (sortOption) {
            case VIEW_DESC:
                sort = Sort.by("view").descending();
                break;
            case LIKE_DESC:
                sort = Sort.by("likeCount").descending();
                break;
            case BOOKMARK_DESC:
                sort = Sort.by("bookmarkCount").descending();
                break;
            default:
                sort = Sort.by("updatedAt").descending();
                break;
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        return postRepository.findPostSummaries(pageable);
    }

    /**
     * 포스트를 불러옵니다.
     *
     * <p>{@link FetchOption}를 통해 자식 엔티티들을 불러올 수 있습니다.
     *
     * @param postId
     * @param commentFetchOption 댓글 조회 방식
     * @param likeFetchOption 좋아요 조회 방식
     * @param bookmarkFetchOption 북마크 조회 방식
     * @return {@link PostDto} 포스트 데이터
     */
    public PostDto getPost(
            Long postId,
            FetchOption commentFetchOption,
            FetchOption likeFetchOption,
            FetchOption bookmarkFetchOption) {
        Post post = fromPostId(postId);
        PostPath postPath = post.getPath();
        List<PostAttachment> postAttachment = post.getAttachments();

        PostDto postDto =
                new PostDto(post).withPathEntity(postPath).withAttachmentEntities(postAttachment);

        // 댓글
        switch (commentFetchOption) {
            case COUNT:
                long commentCount = postCommentRepository.countByPostId(postId);
                postDto.setCommentCount(commentCount);
                break;
            case FULL:
                List<PostComment> commentList = post.getComments();
                postDto.setCommentCount(commentList.size());
                postDto.setComments(PostCommentDto.fromEntities(commentList));
                break;
            default:
                break;
        }

        // 좋아요
        switch (likeFetchOption) {
            case COUNT:
                long likeCount = postLikeRepository.countByPostId(postId);
                postDto.setLikeCount(likeCount);
            case FULL:
                // Not supported
                break;
            default:
                break;
        }

        // 북마크
        switch (bookmarkFetchOption) {
            case COUNT:
                long bookmarkCount = postBookmarkRepository.countByPostId(postId);
                postDto.setBookmarkCount(bookmarkCount);
            case FULL:
                // Not supported
                break;
            default:
                break;
        }

        return postDto;
    }

    /**
     * 포스트를 새로 작성합니다.
     *
     * @param authorId
     * @param postData
     * @param pathThumbnail
     * @param attachments
     * @return {@link PostDto} 작성된 포스트
     */
    @Transactional
    public PostDto writePost(
            String authorId,
            PostDto postData,
            MultipartFile pathThumbnail,
            MultipartFile[] attachments) {
        User author = userService.fromUserId(authorId);
        Post newPost = new Post(author, postData.getTitle(), postData.getContent());

        // 패스 저장
        if (postData.getPath() != null) {
            FileMetaDto newPathThumbnailDto = storageService.store(pathThumbnail, author);
            File newPathThumbnail =
                    fileRepository
                            .findById(newPathThumbnailDto.getFilename())
                            .orElseThrow(
                                    () ->
                                            new StorageFileNotFoundException(
                                                    newPathThumbnailDto.getFilename()));

            LineString pathLineString = PostPathPointDto.toLineString(postData.getPath());
            PostPath newPostPath = new PostPath(newPost, pathLineString, newPathThumbnail);

            newPost.setPath(newPostPath);
        }

        // 첨부파일 저장
        List<PostAttachment> newPostAttachments = new ArrayList<>();
        if (attachments != null && attachments.length > 0) {
            List<FileMetaDto> newAttachmentDtos = storageService.storeAll(attachments, author);
            List<File> newAttachments =
                    fileRepository.findAllById(
                            newAttachmentDtos.stream().map(FileMetaDto::getFilename).toList());

            for (File attachment : newAttachments) {
                newPostAttachments.add(new PostAttachment(newPost, attachment));
            }

            newPost.setAttachments(newPostAttachments);
        }

        // DB 저장
        Post savedPost = postRepository.save(newPost);

        // 반환
        PostPath savedPostPath = savedPost.getPath();
        List<PostAttachment> savedPostAttachment = savedPost.getAttachments();

        return new PostDto(savedPost)
                .withPathEntity(savedPostPath)
                .withAttachmentEntities(savedPostAttachment);
    }

    /**
     * 포스트를 수정합니다.
     *
     * <p>작성자가 아니라면 수정할 수 없습니다.
     *
     * @param authorId
     * @param postData
     * @return {@link PostDto} 수정된 포스트
     */
    public PostDto editPost(
            String authorId,
            PostDto postData,
            MultipartFile pathThumbnail,
            MultipartFile[] attachments) {
        Post post = fromPostId(postData.getId());
        User author = post.getAuthor();

        if (!author.getId().equals(authorId)) {
            throw new UnauthorizedAccessException(
                    String.format("User %s not owning post %d", authorId, postData.getId()));
        }

        post.setTitle(postData.getTitle());
        post.setContent(postData.getContent());

        // 패스 저장
        post.setPath(null); // Trigger removal
        if (postData.getPath() != null) {
            FileMetaDto newPathThumbnailDto = storageService.store(pathThumbnail, author);
            File newPathThumbnail =
                    fileRepository
                            .findById(newPathThumbnailDto.getFilename())
                            .orElseThrow(
                                    () ->
                                            new StorageFileNotFoundException(
                                                    newPathThumbnailDto.getFilename()));

            LineString pathLineString = PostPathPointDto.toLineString(postData.getPath());
            PostPath newPostPath = new PostPath(post, pathLineString, newPathThumbnail);

            post.setPath(newPostPath);
        }

        // 첨부파일 저장
        post.setAttachments(null); // Trigger removal
        if (attachments != null && attachments.length > 0) {
            List<PostAttachment> newPostAttachments = new ArrayList<>();
            List<FileMetaDto> newAttachmentDtos = storageService.storeAll(attachments, author);
            List<File> newAttachments =
                    fileRepository.findAllById(
                            newAttachmentDtos.stream().map(FileMetaDto::getFilename).toList());

            for (File attachment : newAttachments) {
                newPostAttachments.add(new PostAttachment(post, attachment));
            }

            post.setAttachments(newPostAttachments);
        }

        Post editedPost = postRepository.save(post);

        // 반환
        PostPath savedPostPath = editedPost.getPath();
        List<PostAttachment> savedPostAttachment = editedPost.getAttachments();

        return new PostDto(editedPost)
                .withPathEntity(savedPostPath)
                .withAttachmentEntities(savedPostAttachment);
    }

    /**
     * 포스트를 삭제합니다.
     *
     * <p>작성자가 아니라면 삭제할 수 없습니다.
     *
     * @param authorId
     * @param postId
     */
    public void deletePost(String authorId, Long postId) {
        Post post = fromPostId(postId);

        if (!post.getAuthor().getId().equals(authorId)) {
            throw new UnauthorizedAccessException(
                    String.format("User %s not owning post %d", authorId, postId));
        }

        postRepository.delete(post);
    }

    /**
     * 포스트에 좋아요를 추가합니다.
     *
     * @param userId
     * @param postId
     */
    @Transactional
    public void addPostLike(String userId, Long postId) {
        User user = userService.fromUserId(userId);
        Post post = fromPostId(postId);

        if (postLikeRepository.existsById(new PostLikeId(userId, postId))) {
            return;
        }

        PostLike like = new PostLike(user, post);

        postLikeRepository.save(like);
    }

    /**
     * 포스트에 좋아요를 삭제합니다.
     *
     * <p>없는 경우 무시합니다.
     *
     * @param userId
     * @param postId
     */
    @Transactional
    public void removePostLike(String userId, Long postId) {
        postLikeRepository.deleteById(new PostLikeId(userId, postId));
    }

    /**
     * 포스트에 북마크를 추가합니다.
     *
     * @param userId
     * @param postId
     */
    @Transactional
    public void addPostBookmark(String userId, Long postId) {
        User user = userService.fromUserId(userId);
        Post post = fromPostId(postId);

        if (postBookmarkRepository.existsById(new PostBookmarkId(userId, postId))) {
            return;
        }

        PostBookmark bookmark = new PostBookmark(user, post);

        postBookmarkRepository.save(bookmark);
    }

    /**
     * 포스트에 북마크를 삭제합니다.
     *
     * <p>없는 경우 무시합니다.
     *
     * @param userId
     * @param postId
     */
    @Transactional
    public void removePostBookmark(String userId, Long postId) {
        postBookmarkRepository.deleteById(new PostBookmarkId(userId, postId));
    }
}
