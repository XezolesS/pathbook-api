package com.pathbook.pathbook_api.handler;

import com.pathbook.pathbook_api.exception.UserAlreadyExistsException;
import com.pathbook.pathbook_api.exception.UserNotFoundException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        HttpStatus status = HttpStatus.CONFLICT;
        ProblemDetail problemDetail =
                initProblemDetail(status, "/user-already-exists", "User already exists");
        problemDetail.setDetail(ex.getLocalizedMessage());

        String userId = ex.getUserId();
        if (userId != null) {
            problemDetail.setProperty("userid", userId);
        }

        String email = ex.getEmail();
        if (email != null) {
            problemDetail.setProperty("email", email);
        }

        return new ResponseEntity<>(problemDetail, status);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleUserNotFound(UserNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemDetail problemDetail =
                initProblemDetail(status, "/user-not-found", "User not found");
        problemDetail.setDetail(ex.getLocalizedMessage());

        String userId = ex.getUserId();
        if (userId != null) {
            problemDetail.setProperty("userid", userId);
        }

        String email = ex.getEmail();
        if (email != null) {
            problemDetail.setProperty("email", email);
        }

        return new ResponseEntity<>(problemDetail, status);
    }
}
