package com.pathbook.pathbook_api.handler;

import com.pathbook.pathbook_api.exception.StorageFileNotFoundException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class StorageExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleStorageFileNotFound(
            StorageFileNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemDetail problemDetail =
                initProblemDetail(status, "/file-not-found", "File not found");
        problemDetail.setDetail(ex.getLocalizedMessage());

        String filename = ex.getFilename();
        if (filename != null) {
            problemDetail.setProperty("filename", filename);
        }

        return new ResponseEntity<>(problemDetail, status);
    }
}
