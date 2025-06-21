package com.pathbook.pathbook_api.dto.response;

import com.pathbook.pathbook_api.dto.UserInfo;
import com.pathbook.pathbook_api.dto.UserInfoDto;

/** 사용자 데이터를 응답으로 반환하기 위한 래퍼 클래스입니다. */
public class UserResponse extends UserInfoDto {
    public UserResponse() {}

    public UserResponse(UserInfo data) {
        super(data);
    }
}
