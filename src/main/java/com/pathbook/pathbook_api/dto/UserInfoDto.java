package com.pathbook.pathbook_api.dto;

import com.pathbook.pathbook_api.entity.User;

import java.time.LocalDate;

public class UserInfoDto implements UserInfo {
    private String id;
    private String email;
    private String username;
    private String sex;
    private LocalDate birthDate;
    private String bio;

    public UserInfoDto() {}

    public UserInfoDto(User entity) {
        this(
                entity.getId(),
                entity.getEmail(),
                entity.getUsername(),
                entity.getSex(),
                entity.getBirthDate(),
                entity.getBio());
    }

    public UserInfoDto(UserInfo data) {
        this(
                data.getId(),
                data.getEmail(),
                data.getUsername(),
                data.getSex(),
                data.getBirthDate(),
                data.getBio());
    }

    public UserInfoDto(
            String id, String email, String username, String sex, LocalDate birthDate, String bio) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.sex = sex;
        this.birthDate = birthDate;
        this.bio = bio;
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
}
