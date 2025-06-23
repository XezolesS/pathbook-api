package com.pathbook.pathbook_api.handler;

import com.pathbook.pathbook_api.exception.PostNotFoundException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PostExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ProblemDetail> handlePostNotFound(PostNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemDetail problemDetail =
                initProblemDetail(status, "/post-not-found", "Post not found");
        problemDetail.setDetail(ex.getLocalizedMessage());

        Long postId = ex.getPostId();
        if (postId != null) {
            problemDetail.setProperty("postid", postId);
        }

        return new ResponseEntity<>(problemDetail, status);
    }
}
