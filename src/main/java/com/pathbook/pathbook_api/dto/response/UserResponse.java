package com.pathbook.pathbook_api.dto.response;

import com.pathbook.pathbook_api.entity.User;

public class UserResponse {
    private String userId;
    private String username;
    private String email;
    private boolean verified;
    private String iconUrl;
    private String bannerUrl;

    public UserResponse(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.verified = user.getIsVerified();
        this.iconUrl = user.getIconUrl();
        this.bannerUrl = user.getBannerUrl();
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public boolean isVerified() {
        return verified;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }
}
