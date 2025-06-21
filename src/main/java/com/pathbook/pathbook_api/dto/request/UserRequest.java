package com.pathbook.pathbook_api.dto.request;

import com.pathbook.pathbook_api.dto.UserData;
import com.pathbook.pathbook_api.dto.UserDataDto;

/** 사용자 데이터를 요청으로 받기 위한 래퍼 클래스입니다. */
public class UserRequest extends UserDataDto {
    public UserRequest() {}

    public UserRequest(UserData data) {
        super(data);
    }
}
