package com.pathbook.pathbook_api.dto.response;

import com.pathbook.pathbook_api.dto.FileMetaDto;
import com.pathbook.pathbook_api.entity.File;

/** 파일 메타데이터를 응답으로 반환하기 위한 래퍼 클래스입니다. */
public class FileMetaResponse extends FileMetaDto {
    public FileMetaResponse() {}

    public FileMetaResponse(FileMetaDto dto) {
        super(dto);
    }

    public FileMetaResponse(File entity) {
        super(entity);
    }
}
