package com.pathbook.pathbook_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.pathbook.pathbook_api.dto.UserPrincipal;
import com.pathbook.pathbook_api.entity.PostComment;
import com.pathbook.pathbook_api.exception.PostCommentNotFoundException;
import com.pathbook.pathbook_api.exception.UnauthorizedAccessException;
import com.pathbook.pathbook_api.request.CommentRequest;
import com.pathbook.pathbook_api.service.PostCommentService;

import java.util.List;

@RestController
@RequestMapping("/posts/{postId}/comments")
public class PostCommentController {

    @Autowired
    private PostCommentService postCommentService;

    @GetMapping
    public ResponseEntity<List<PostComment>> getCommentsByPostId(@PathVariable Integer postId) {
        return ResponseEntity.ok(postCommentService.getCommentsByPostId(postId));
    }

    @PostMapping
    public ResponseEntity<PostComment> createComment(
            @AuthenticationPrincipal UserPrincipal user,
            @PathVariable Integer postId,
            @RequestBody CommentRequest request) {
        PostComment comment = postCommentService.createComment(
                user.getId(),
                postId,
                request.content()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(
            @AuthenticationPrincipal UserPrincipal user,
            @PathVariable Integer postId,
            @PathVariable Integer commentId,
            @RequestBody CommentRequest request) {
        try {
            PostComment updatedComment = postCommentService.updateComment(
                    commentId,
                    user.getId(),
                    request.content()
            );
            return ResponseEntity.ok(updatedComment);
        } catch (PostCommentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(
            @AuthenticationPrincipal UserPrincipal user,
            @PathVariable Integer postId,
            @PathVariable Integer commentId) {
        try {
            postCommentService.deleteComment(commentId, user.getId());
            return ResponseEntity.noContent().build();
        } catch (PostCommentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping("/{commentId}/like")
    public ResponseEntity<String> likeComment(
            @AuthenticationPrincipal UserPrincipal user,
            @PathVariable Integer commentId) {
        postCommentService.likeComment(user.getId(), commentId);
        return ResponseEntity.ok("댓글 좋아요 처리 완료");
    }

    @DeleteMapping("/{commentId}/like")
    public ResponseEntity<String> unlikeComment(
            @AuthenticationPrincipal UserPrincipal user,
            @PathVariable Integer commentId) {
        postCommentService.unlikeComment(user.getId(), commentId);
        return ResponseEntity.ok("댓글 좋아요 취소 완료");
    }
}
