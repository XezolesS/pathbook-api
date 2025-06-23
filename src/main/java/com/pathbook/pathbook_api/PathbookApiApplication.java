package com.pathbook.pathbook_api;

import com.pathbook.pathbook_api.storage.StorageProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class PathbookApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(PathbookApiApplication.class, args);
    }
}
