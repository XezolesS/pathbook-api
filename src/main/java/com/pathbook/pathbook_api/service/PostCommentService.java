package com.pathbook.pathbook_api.service;

import com.pathbook.pathbook_api.dto.PostCommentDto;
import com.pathbook.pathbook_api.entity.Post;
import com.pathbook.pathbook_api.entity.PostComment;
import com.pathbook.pathbook_api.entity.PostCommentLike;
import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.entity.id.PostCommentLikeId;
import com.pathbook.pathbook_api.exception.PostCommentNotFoundException;
import com.pathbook.pathbook_api.exception.PostNotFoundException;
import com.pathbook.pathbook_api.exception.UnauthorizedAccessException;
import com.pathbook.pathbook_api.repository.PostCommentLikeRepository;
import com.pathbook.pathbook_api.repository.PostCommentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostCommentService {
    @Autowired private UserService userService;

    @Autowired private PostService postService;

    @Autowired private PostCommentRepository postCommentRepository;

    @Autowired private PostCommentLikeRepository postCommentLikeRepository;

    /**
     * 댓글의 ID로부터 댓글 엔티티를 불러옵니다.
     *
     * @param commentId
     * @return {@link PostComment} 댓글 엔티티
     */
    public PostComment fromCommentId(Long commentId) {
        return postCommentRepository
                .findById(commentId)
                .orElseThrow(() -> new PostCommentNotFoundException(commentId));
    }

    /**
     * 포스트에 달린 댓글들을 전부 불러옵니다.
     *
     * @param postId
     * @return
     */
    public List<PostCommentDto> getPostComments(Long postId) {
        if (!postService.existsByPostId(postId)) {
            throw new PostNotFoundException(postId);
        }

        return postCommentRepository.findAllByPostId(postId).stream()
                .map(PostCommentDto::new)
                .toList();
    }

    /**
     * 댓글의 ID로부터 댓글을 불러옵니다.
     *
     * @param commentId
     * @return
     */
    public PostCommentDto getPostComment(Long commentId) {
        return new PostCommentDto(fromCommentId(commentId));
    }

    /**
     * 댓글을 새로 작성합니다.
     *
     * @param authorId
     * @param postCommentData
     * @return {@link PostCommentDto} 작성된 댓글
     */
    public PostCommentDto writePostComment(String authorId, PostCommentDto postCommentData) {
        User author = userService.fromUserId(authorId);
        Post post = postService.fromPostId(postCommentData.getPostId());

        PostComment newPostComment = new PostComment(post, author, postCommentData.getContent());

        PostComment savedPostComment = postCommentRepository.save(newPostComment);

        return new PostCommentDto(savedPostComment);
    }

    /**
     * 댓글을 수정합니다.
     *
     * <p>작성자가 아니라면 수정할 수 없습니다.
     *
     * @param authorId
     * @param postCommentData
     * @return {@link PostCommentDto} 수정된 댓글
     */
    public PostCommentDto editPostComment(String authorId, PostCommentDto postCommentData) {
        PostComment postComment = fromCommentId(postCommentData.getId());

        if (!postComment.getAuthor().getId().equals(authorId)) {
            throw new UnauthorizedAccessException(authorId);
        }

        postComment.setContent(postCommentData.getContent());

        PostComment editedPostComment = postCommentRepository.save(postComment);

        return new PostCommentDto(editedPostComment);
    }

    /**
     * 댓글을 삭제합니다.
     *
     * <p>작성자가 아니라면 삭제할 수 없습니다.
     *
     * @param authorId
     * @param postCommentData
     */
    public void deletePostComment(String authorId, Long commentId) {
        PostComment postComment = fromCommentId(commentId);

        if (!postComment.getAuthor().getId().equals(authorId)) {
            throw new UnauthorizedAccessException(authorId);
        }

        postCommentRepository.delete(postComment);
    }

    /**
     * 댓글에 좋아요를 추가합니다.
     *
     * @param userId
     * @param commentId
     */
    @Transactional
    public void addPostCommentLike(String userId, Long commentId) {
        User user = userService.fromUserId(userId);
        PostComment postComment = fromCommentId(commentId);

        if (postCommentLikeRepository.existsById(new PostCommentLikeId(userId, commentId))) {
            return;
        }

        PostCommentLike like = new PostCommentLike(user, postComment);

        postCommentLikeRepository.save(like);
    }

    /**
     * 댓글에 좋아요를 삭제합니다.
     *
     * <p>없는 경우 무시합니다.
     *
     * @param userId
     * @param commentId
     */
    @Transactional
    public void removePostCommentLike(String userId, Long commentId) {
        postCommentLikeRepository.deleteById(new PostCommentLikeId(userId, commentId));
    }
}
