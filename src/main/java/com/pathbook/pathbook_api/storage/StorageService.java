package com.pathbook.pathbook_api.storage;

import com.pathbook.pathbook_api.dto.FileDto;
import com.pathbook.pathbook_api.dto.FileMeta;
import com.pathbook.pathbook_api.dto.UserInfo;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
    /**
     * 스토리지를 초기화합니다.
     *
     * <p>스토리지에 대한 셋업 로직이 포함되어야 합니다.
     */
    void init();

    /**
     * 파일을 스토리지에 저장합니다.
     *
     * <p>{@code owner != null}이면 파일에 소유자를 지정합니다.
     *
     * @param file 파일
     * @param owner 소유자
     * @return 저장된 파일의 엔티티
     */
    FileMeta store(MultipartFile file, UserInfo owner);

    /**
     * 스토리지에 있는 모든 파일들의 경로를 반환합니다.
     *
     * @return 파일의 경로 스트림
     */
    Stream<Path> loadAll();

    /**
     * 스토리지에서 {@code filename}와 일치하는 파일의 메타데이터를 불러옵니다.
     *
     * @param filename 파일 이름
     * @return 파일 메타데이터
     */
    FileMeta load(String filename);

    /**
     * 스토리지에서 {@code filename}와 일치하는 파일의 경로를 불러옵니다.
     *
     * @param filename 파일 이름
     * @return 파일 경로
     */
    Path loadAsPath(String filename);

    /**
     * 스토리지에서 {@code filename}와 일치하는 파일을 완전히 불러옵니다. 파일의 컨텐츠는 {@link Resource} 타입입니다.
     *
     * @param filename 파일 이름
     * @return {@link FileDto} 파일 DTO
     */
    FileDto loadFull(String filename);

    /** 스토리지에 있는 모든 파일을 삭제합니다. */
    void deleteAll();

    /**
     * 스토리지에서 {@code filename}와 일치하는 파일을 삭제합니다.
     *
     * @param filename 파일 이름
     */
    void delete(String filename);
}
