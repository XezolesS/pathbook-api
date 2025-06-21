package com.pathbook.pathbook_api.dto;

import java.time.LocalDateTime;

/**
 * 파일의 메타데이터를 위한 인터페이스입니다. private 필드를 만들어 getter 구현을 포함하여야 합니다.
 *
 * <p>파일의 컨텐츠 자체는 포함하지 않습니다.
 */
public interface FileMeta {
    /**
     * 파일의 이름입니다.
     *
     * @return filename
     */
    public String getFilename();

    /**
     * 파일 소유자의 ID입니다.
     *
     * @return ownerId
     */
    public String getOwnerId();

    /**
     * 파일의 원래 이름입니다.
     *
     * @return originalFilename
     */
    public String getOriginalFilename();

    /**
     * 파일의 컨텐츠 타입입니다.
     *
     * @return contentType
     */
    public String getContentType();

    /**
     * 파일의 크기입니다.
     *
     * <p>단위는 byte입니다.
     *
     * @return size
     */
    public Long getSize();

    /**
     * 파일의 추가 날짜입니다.
     *
     * @return createdAt
     */
    public LocalDateTime getCreatedAt();
}
