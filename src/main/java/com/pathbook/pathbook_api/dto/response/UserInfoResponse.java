package com.pathbook.pathbook_api.dto.response;

import com.pathbook.pathbook_api.dto.UserInfoDto;
import com.pathbook.pathbook_api.entity.User;

/** 사용자 데이터를 응답으로 반환하기 위한 래퍼 클래스입니다. */
public class UserInfoResponse extends UserInfoDto {
    public UserInfoResponse() {}

    public UserInfoResponse(UserInfoDto dto) {
        super(dto);
    }

    public UserInfoResponse(User entity) {
        super(entity);
    }
}
