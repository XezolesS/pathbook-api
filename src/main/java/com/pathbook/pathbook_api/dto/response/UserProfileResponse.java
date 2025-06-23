package com.pathbook.pathbook_api.dto.response;

import com.pathbook.pathbook_api.dto.UserInfoDto;
import com.pathbook.pathbook_api.entity.User;

/** 사용자 프로필 정보를 응답으로 반환하기 위한 래퍼 클래스입니다. */
public class UserProfileResponse extends UserInfoResponse {
    private FileMetaResponse icon;
    private FileMetaResponse banner;

    public UserProfileResponse() {}

    public UserProfileResponse(UserInfoDto data) {
        this(data, null, null);
    }

    public UserProfileResponse(User entity) {
        this(
                new UserInfoDto(entity),
                new FileMetaResponse(entity.getIconFile()),
                new FileMetaResponse(entity.getBannerFile()));
    }

    public UserProfileResponse(UserInfoDto data, FileMetaResponse icon, FileMetaResponse banner) {
        super(data);

        this.icon = icon;
        this.banner = banner;
    }

    public FileMetaResponse getIcon() {
        return icon;
    }

    public void setIcon(FileMetaResponse icon) {
        this.icon = icon;
    }

    public FileMetaResponse getBanner() {
        return banner;
    }

    public void setBanner(FileMetaResponse banner) {
        this.banner = banner;
    }
}
