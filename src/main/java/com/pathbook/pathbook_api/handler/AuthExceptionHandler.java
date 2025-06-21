package com.pathbook.pathbook_api.handler;

import com.pathbook.pathbook_api.exception.PasswordMismatchException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthExceptionHandler extends BaseExceptionHandler {
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
}
