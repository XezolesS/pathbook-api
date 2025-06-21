package com.pathbook.pathbook_api.dto.response;

import com.pathbook.pathbook_api.dto.UserData;
import com.pathbook.pathbook_api.dto.UserPrincipal;
import com.pathbook.pathbook_api.entity.User;

import java.time.LocalDate;

public record UserResponse(
        String userId,
        String email,
        String username,
        String getSex,
        LocalDate getBirthDate,
        String getBio,
        String iconUrl,
        String bannerUrl) {
    public static UserResponse fromUserDataLike(UserData userData) {
        return new UserResponse(
                userData.getId(),
                userData.getEmail(),
                userData.getUsername(),
                userData.getSex(),
                userData.getBirthDate(),
                userData.getBio(),
                userData.getIconUrl(),
                userData.getBannerUrl());
    }

    public static UserResponse fromUser(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getSex(),
                user.getBirthDate(),
                user.getBio(),
                user.getIconUrl(),
                user.getBannerUrl());
    }

    public static UserResponse fromUserPrincipal(UserPrincipal userPrincipal) {
        return new UserResponse(
                userPrincipal.getId(),
                userPrincipal.getEmail(),
                userPrincipal.getUsername(),
                userPrincipal.getSex(),
                userPrincipal.getBirthDate(),
                userPrincipal.getBio(),
                userPrincipal.getIconUrl(),
                userPrincipal.getBannerUrl());
    }
}
