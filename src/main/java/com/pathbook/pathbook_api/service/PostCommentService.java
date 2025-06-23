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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        // 부모 댓글이 있는 경우 불러오기
        Long parentId = postCommentData.getParentId();
        PostComment parent = null;
        if (parentId != null) {
            parent =
                    postCommentRepository
                            .findById(postCommentData.getParentId())
                            .orElseThrow(() -> new PostCommentNotFoundException(parentId));
        }

        PostComment newPostComment =
                new PostComment(post, parent, author, postCommentData.getContent());

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

    /**
     * {@link PostComment} 엔티티 리스트로부터 코멘트를 트리형식으로 매핑합니다.
     *
     * <p>{@link PostCommentDto}가 노드 역할을 하며, 각 인스턴스에 자식 {@link PostCommentDto}가 포함됩니다.
     *
     * @param commentEntities
     * @return {@link PostCommentDto} 루트 노드 리스트
     */
    public List<PostCommentDto> buildCommentTreeFromEntities(List<PostComment> commentEntities) {
        Map<Long, PostCommentDto> commentMap = new HashMap<>();
        List<PostCommentDto> roots = new ArrayList<>();

        for (PostComment comment : commentEntities) {
            commentMap.put(comment.getId(), new PostCommentDto(comment));
        }

        for (PostComment comment : commentEntities) {
            PostCommentDto commentDto = new PostCommentDto(commentMap.get(comment.getId()));
            Long parentId = comment.getParent().getId();

            if (parentId == null) {
                roots.add(commentDto);
            } else {
                PostCommentDto parent = commentMap.get(parentId);

                if (parent == null) {
                    throw new PostCommentNotFoundException(parentId);
                }

                parent.addReply(commentDto);
            }
        }

        return roots;
    }

    /**
     * {@link PostCommentDto} DTO 리스트로부터 코멘트를 트리형식으로 매핑합니다.
     *
     * <p>{@link PostCommentDto}가 노드 역할을 하며, 각 인스턴스에 자식 {@link PostCommentDto}가 포함됩니다.
     *
     * @param commentEntities
     * @return {@link PostCommentDto} 루트 노드 리스트
     */
    public List<PostCommentDto> buildCommentTreeFromDto(List<PostCommentDto> commentDtos) {
        Map<Long, PostCommentDto> commentMap = new HashMap<>();
        List<PostCommentDto> roots = new ArrayList<>();

        for (PostCommentDto comment : commentDtos) {
            commentMap.put(comment.getId(), comment);

            if (comment.getReplies() != null && comment.getReplies().size() > 0) {
                comment.getReplies().clear();
            }
        }

        for (PostCommentDto comment : commentDtos) {
            Long parentId = comment.getParentId();

            if (parentId == null) {
                roots.add(comment);
            } else {
                PostCommentDto parent = commentMap.get(parentId);

                if (parent == null) {
                    throw new PostCommentNotFoundException(parentId);
                }

                parent.addReply(comment);
            }
        }

        return roots;
    }
}
