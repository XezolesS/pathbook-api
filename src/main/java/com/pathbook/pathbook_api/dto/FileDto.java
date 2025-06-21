package com.pathbook.pathbook_api.dto;

import org.springframework.core.io.Resource;

import java.nio.file.Path;

public class FileDto extends FileMetaDto {
    private Path absolutePath;
    private Resource resource;

    public FileDto() {}

    public FileDto(FileMeta fileMeta, Path absoultePath, Resource resource) {
        super(fileMeta);
        this.absolutePath = absoultePath;
        this.resource = resource;
    }

    public Path getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(Path absolutePath) {
        this.absolutePath = absolutePath;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource fileResource) {
        this.resource = fileResource;
    }
}
