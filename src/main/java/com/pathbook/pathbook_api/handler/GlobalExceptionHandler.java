package com.pathbook.pathbook_api.handler;

import com.pathbook.pathbook_api.exception.PasswordMismatchException;
import com.pathbook.pathbook_api.exception.UserAlreadyExistsException;
import com.pathbook.pathbook_api.exception.UserNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private int serverPort;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGlobalException(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemDetail problemDetail =
                initProblemDetail(status, "/internal-server-error", "Internal server error");
        problemDetail.setDetail(ex.getLocalizedMessage());

        return new ResponseEntity<>(problemDetail, status);
    }

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

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ProblemDetail> handlePasswordMismatch(PasswordMismatchException ex) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ProblemDetail problemDetail =
                initProblemDetail(status, "/password-mismatch", "Password mismatch");
        problemDetail.setDetail(ex.getLocalizedMessage());

        String userId = ex.getUserId();
        if (userId != null) {
            problemDetail.setProperty("userid", userId);
        }

        return new ResponseEntity<>(problemDetail, status);
    }

    private ProblemDetail initProblemDetail(HttpStatusCode status, String type, String title) {
        String serverUrl = String.format("%s:%d", serverAddress, serverPort);

        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setType(URI.create(serverUrl + type));
        problemDetail.setTitle(title);
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }
}
