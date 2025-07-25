package com.pathbook.pathbook_api.dto;

import com.pathbook.pathbook_api.entity.File;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class FileMetaDto {
    private String filename;
    private String ownerId;
    private String originalFilename;
    private String contentType;
    private Long size;
    private LocalDateTime createdAt;

    public FileMetaDto() {}

    public FileMetaDto(File entity) {
        this(
                entity.getFilename(),
                entity.getOwnerId(),
                entity.getOriginalFilename(),
                entity.getContentType(),
                entity.getSize(),
                entity.getCreatedAt());
    }

    public FileMetaDto(FileMetaDto dto) {
        this(
                dto.filename,
                dto.ownerId,
                dto.originalFilename,
                dto.contentType,
                dto.size,
                dto.createdAt);
    }

    public FileMetaDto(
            String filename,
            String ownerId,
            String originalFilename,
            String contentType,
            Long size,
            LocalDateTime createdAt) {
        this.filename = filename;
        this.ownerId = ownerId;
        this.originalFilename = originalFilename;
        this.contentType = contentType;
        this.size = size;
        this.createdAt = createdAt;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static List<FileMetaDto> fromEntities(List<File> entities) {
        return entities.stream().map(FileMetaDto::new).collect(Collectors.toList());
    }
}
