package com.pathbook.pathbook_api.handler;

import com.pathbook.pathbook_api.exception.PostCommentNotFoundException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PostCommentExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler(PostCommentNotFoundException.class)
    public ResponseEntity<ProblemDetail> handlePostCommentNotFound(
            PostCommentNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemDetail problemDetail =
                initProblemDetail(status, "/post-comment-not-found", "Post comment not found");
        problemDetail.setDetail(ex.getLocalizedMessage());

        Long commentId = ex.getCommentId();
        if (commentId != null) {
            problemDetail.setProperty("commentid", commentId);
        }

        return new ResponseEntity<>(problemDetail, status);
    }
}
