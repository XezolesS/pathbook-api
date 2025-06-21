package com.pathbook.pathbook_api.handler;

import com.pathbook.pathbook_api.exception.RecordAlreadyExistsException;
import com.pathbook.pathbook_api.exception.RecordNotFoundException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler extends BaseExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGlobalException(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemDetail problemDetail =
                initProblemDetail(status, "/internal-server-error", "Internal server error");
        problemDetail.setDetail(ex.getLocalizedMessage());

        return new ResponseEntity<>(problemDetail, status);
    }

    @ExceptionHandler(RecordAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleRecordAlreadyExists(
            RecordAlreadyExistsException ex) {
        HttpStatus status = HttpStatus.CONFLICT;
        ProblemDetail problemDetail =
                initProblemDetail(status, "/record-already-exists", "Record already exists");
        problemDetail.setDetail(ex.getLocalizedMessage());

        return new ResponseEntity<>(problemDetail, status);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleRecordNotFound(RecordNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemDetail problemDetail =
                initProblemDetail(status, "/record-not-found", "Record not found");
        problemDetail.setDetail(ex.getLocalizedMessage());

        return new ResponseEntity<>(problemDetail, status);
    }
}
