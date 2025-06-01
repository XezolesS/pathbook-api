package com.pathbook.pathbook_api.controller;

import com.pathbook.pathbook_api.dto.CommentReportRequest;
import com.pathbook.pathbook_api.dto.UserPrincipal;
import com.pathbook.pathbook_api.entity.Comment;
import com.pathbook.pathbook_api.exception.CommentNotFoundException;
import com.pathbook.pathbook_api.exception.UnauthorizedAccessException;
import com.pathbook.pathbook_api.request.CommentRequest;
import com.pathbook.pathbook_api.service.CommentReportService;
import com.pathbook.pathbook_api.service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired private CommentService commentService;

    @Autowired private CommentReportService commentReportService;

    @GetMapping("/list/{postId}")
    public ResponseEntity<List<Comment>> getCommentList(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentListByPostId(postId));
    }

    @PostMapping("/write")
    public ResponseEntity<Comment> writeComment(
            @AuthenticationPrincipal UserPrincipal user, @RequestBody CommentRequest request) {
        Comment resultComment =
                commentService.writeComment(user.getId(), request.postId(), request.content());

        return ResponseEntity.status(HttpStatus.CREATED).body(resultComment);
    }

    @PutMapping("/update/{commentId}")
    public ResponseEntity<?> updateComment(
            @AuthenticationPrincipal UserPrincipal user,
            @PathVariable Long commentId,
            @RequestBody CommentRequest request) {
        try {
            Comment resultComment =
                    commentService.updateComment(commentId, user.getId(), request.content());

            return ResponseEntity.ok(resultComment);
        } catch (CommentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e);
        }
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<?> deleteComment(
            @AuthenticationPrincipal UserPrincipal user, @PathVariable Long commentId) {
        try {
            commentService.deleteComment(user.getId(), commentId);
            return ResponseEntity.ok().build();
        } catch (CommentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e);
        }
    }

    @PostMapping("/like/{commentId}")
    public ResponseEntity<String> likeComment(
            @AuthenticationPrincipal UserPrincipal user, @PathVariable Long commentId) {
        commentService.likeComment(user.getId(), commentId);
        return ResponseEntity.ok("Liked successfully.");
    }

    @PostMapping("/unlike/{commentId}")
    public ResponseEntity<String> unlikeComment(
            @AuthenticationPrincipal UserPrincipal user, @PathVariable Long commentId) {
        commentService.unlikeComment(user.getId(), commentId);
        return ResponseEntity.ok("Unliked successfully.");
    }

    @PostMapping("/report/{commentId}")
    public ResponseEntity<?> reportComment(@RequestBody CommentReportRequest request) {
        commentReportService.reportComment(
                request.commentId(),
                request.reporterId(),
                request.reason(),
                request.detailReason());
        return ResponseEntity.ok("Comment Reported successfully.");
    }
}
