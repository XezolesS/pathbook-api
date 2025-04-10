package com.pathbook.pathbook_api.controller;

import com.pathbook.pathbook_api.dto.CommentRequest;
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

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }

    @PostMapping("/add")
    public ResponseEntity<Comment> addComment(@RequestBody CommentRequest request) {
        return ResponseEntity.ok(commentService.addComment(request));
    }

    @PostMapping("/update/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId, @RequestBody CommentRequest request) {
        return ResponseEntity.ok(commentService.updateComment(commentId, request.getContent()));
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/like/{commentId}")
    public ResponseEntity<Void> likeComment(@PathVariable Long commentId) {
        commentService.likeComment(commentId);
        return ResponseEntity.ok().build();
    }
}
