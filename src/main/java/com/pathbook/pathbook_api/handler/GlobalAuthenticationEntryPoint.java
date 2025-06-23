package com.pathbook.pathbook_api.handler;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class GlobalAuthenticationEntryPoint extends BaseExceptionHandler
        implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException, ServletException {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ProblemDetail problemDetail = initProblemDetail(status, "/unauthorized", "Unauthorized");
        problemDetail.setDetail(authException.getLocalizedMessage());

        ResponseCookie expiredCookie =
                ResponseCookie.from("logged_in", "").path("/").maxAge(0).sameSite("Lax").build();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.addHeader(HttpHeaders.SET_COOKIE, expiredCookie.toString());

        String json = new ObjectMapper().writeValueAsString(problemDetail);
        response.getWriter().write(json);
    }
}
