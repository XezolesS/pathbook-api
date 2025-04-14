package com.pathbook.pathbook_api.service;

import com.pathbook.pathbook_api.dto.CommentRequest;
import com.pathbook.pathbook_api.entity.Comment;
import com.pathbook.pathbook_api.entity.UserCommentLike;
import com.pathbook.pathbook_api.repository.CommentRepository;
import com.pathbook.pathbook_api.repository.UserCommentLikeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserCommentLikeRepository userCommentLikeRepository;

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public Comment addComment(CommentRequest request) {
        Comment comment = new Comment(
                request.postId(),
                request.authorId(),
                request.content()
        );
        return commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(Long commentId, String newContent) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
        comment.setContent(newContent);
        comment.setUpdatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Transactional
    public void likeComment(String userId, Long commentId, boolean like) {
        // user_comment_like 테이블 업데이트
        UserCommentLike userCommentLike = userCommentLikeRepository.findByUserIdAndCommentId(userId, commentId)
                .orElse(new UserCommentLike(userId, commentId, false));

        if (userCommentLike.isLike() == like) {
            return;
        }

        userCommentLike.setLike(like);
        userCommentLikeRepository.save(userCommentLike);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        int likeChange = like ? 1 : -1; // 좋아요 추가 시 +1, 취소 시 -1
        comment.setLikes(comment.getLikes() + likeChange);
        commentRepository.save(comment);
    }
}
