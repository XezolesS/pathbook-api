package com.pathbook.pathbook_api.dto;

import com.pathbook.pathbook_api.entity.PostPath;

import java.util.List;

public class PostPathDto {
    List<PostPathPointDto> pathPoints;
    FileMetaDto thumbnail;

    public PostPathDto() {}

    public PostPathDto(PostPath entity) {
        this(
                PostPathPointDto.fromLineString(entity.getPathPoints()),
                new FileMetaDto(entity.getThumbnailFile()));
    }

    public PostPathDto(PostPathDto dto) {
        this(dto.pathPoints, dto.thumbnail);
    }

    public PostPathDto(List<PostPathPointDto> pathPoints, FileMetaDto thumbnail) {
        this.pathPoints = pathPoints;
        this.thumbnail = thumbnail;
    }

    public List<PostPathPointDto> getPathPoints() {
        return pathPoints;
    }

    public void setPathPoints(List<PostPathPointDto> pathPoints) {
        this.pathPoints = pathPoints;
    }

    public FileMetaDto getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(FileMetaDto thumbnail) {
        this.thumbnail = thumbnail;
    }
}
