package com.pathbook.pathbook_api.dto.response;

import com.pathbook.pathbook_api.dto.FileMeta;
import com.pathbook.pathbook_api.dto.UserInfo;
import com.pathbook.pathbook_api.entity.User;

/** 사용자 프로필 정보를 응답으로 반환하기 위한 래퍼 클래스입니다. */
public class UserProfileResponse extends UserResponse {
    private FileMeta icon;
    private FileMeta banner;

    public UserProfileResponse() {}

    public UserProfileResponse(UserInfo data) {
        this(data, null, null);
    }

    public UserProfileResponse(User entity) {
        this(entity, entity.getIconFile(), entity.getBannerFile());
    }

    public UserProfileResponse(UserInfo data, FileMeta icon, FileMeta banner) {
        super(data);

        this.icon = icon;
        this.banner = banner;
    }

    public FileMeta getIcon() {
        return icon;
    }

    public void setIcon(FileMeta icon) {
        this.icon = icon;
    }

    public FileMeta getBanner() {
        return banner;
    }

    public void setBanner(FileMeta banner) {
        this.banner = banner;
    }
}
