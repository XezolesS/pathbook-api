package com.pathbook.pathbook_api.handler;

import java.net.URI;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;

public class BaseExceptionHandler {
    @Value("${server.address}")
    protected String serverAddress;

    @Value("${server.port}")
    protected int serverPort;

    protected ProblemDetail initProblemDetail(HttpStatusCode status, String type, String title) {
        String serverUrl = String.format("%s:%d", serverAddress, serverPort);

        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setType(URI.create(serverUrl + type));
        problemDetail.setTitle(title);
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }
}
