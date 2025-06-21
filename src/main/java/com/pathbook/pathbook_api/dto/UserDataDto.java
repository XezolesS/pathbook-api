package com.pathbook.pathbook_api.dto;


import java.time.LocalDate;

public class UserDataDto implements UserData {
    private String id;
    private String email;
    private String username;
    private String sex;
    private LocalDate birthDate;
    private String bio;
    private String iconUrl;
    private String bannerUrl;

    public UserDataDto() {}

    public UserDataDto(UserData data) {
        this(
                data.getId(),
                data.getEmail(),
                data.getUsername(),
                data.getSex(),
                data.getBirthDate(),
                data.getBio(),
                data.getIconUrl(),
                data.getBannerUrl());
    }

    public UserDataDto(
            String id,
            String email,
            String username,
            String sex,
            LocalDate birthDate,
            String bio,
            String iconUrl,
            String bannerUrl) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.sex = sex;
        this.birthDate = birthDate;
        this.bio = bio;
        this.iconUrl = iconUrl;
        this.bannerUrl = bannerUrl;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @Override
    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }
}
