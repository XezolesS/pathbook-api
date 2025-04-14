package com.pathbook.pathbook_api.controller;

import com.pathbook.pathbook_api.dto.CommentRequest;
import com.pathbook.pathbook_api.dto.LikeRequest;
import com.pathbook.pathbook_api.entity.Comment;
import com.pathbook.pathbook_api.service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{postId}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }

    @PostMapping("/save")
    public ResponseEntity<Comment> addComment(@RequestBody CommentRequest request) {
        validateCommentRequest(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.addComment(request));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId, @RequestBody CommentRequest request) {
        validateCommentRequest(request);

        return ResponseEntity.ok(commentService.updateComment(commentId, request.content()));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/like")
    public ResponseEntity<String> likeComment(@RequestBody LikeRequest likeRequest) {
        validateLikeRequest(likeRequest);

        commentService.likeComment(likeRequest.userId(), likeRequest.commentId(), likeRequest.like());
        return ResponseEntity.ok(likeRequest.like() ? "Liked successfully." : "Unliked successfully.");
    }

    private void validateCommentRequest(CommentRequest request) {
        if (request.postId() == null || request.postId() <= 0) {
            throw new IllegalArgumentException("Invalid postId: must be a positive number.");
        }
        if (request.authorId() == null || request.authorId().isBlank()) {
            throw new IllegalArgumentException("Invalid authorId: must not be null or blank.");
        }
        if (request.content() == null || request.content().isBlank()) {
            throw new IllegalArgumentException("Invalid content: must not be null or blank.");
        }
    }

    private void validateLikeRequest(LikeRequest likeRequest) {
        if (likeRequest.userId() == null || likeRequest.userId().isBlank()) {
            throw new IllegalArgumentException("Invalid userId: must not be null or blank.");
        }
        if (likeRequest.commentId() == null || likeRequest.commentId() <= 0) {
            throw new IllegalArgumentException("Invalid commentId: must be a positive number.");
        }
    }
}
