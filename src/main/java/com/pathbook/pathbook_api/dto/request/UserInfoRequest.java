package com.pathbook.pathbook_api.dto.request;

import com.pathbook.pathbook_api.dto.UserInfo;
import com.pathbook.pathbook_api.dto.UserInfoDto;

/** 사용자 데이터를 요청으로 받기 위한 래퍼 클래스입니다. */
public class UserInfoRequest extends UserInfoDto {
    public UserInfoRequest() {}

    public UserInfoRequest(UserInfo data) {
        super(data);
    }
}
