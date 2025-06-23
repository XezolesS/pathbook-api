package com.pathbook.pathbook_api.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class GlobalAccessDeniedHandler extends BaseExceptionHandler implements AccessDeniedHandler {
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ProblemDetail problemDetail = initProblemDetail(status, "/access-denied", "Access denied");
        problemDetail.setDetail(accessDeniedException.getLocalizedMessage());

        String json = new ObjectMapper().writeValueAsString(problemDetail);
        response.getWriter().write(json);
    }
}
