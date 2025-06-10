package com.pathbook.pathbook_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id", length = 33, nullable = false)
    private String id;

    @Column(name = "email", length = 256, nullable = false, unique = true)
    private String email;

    @Column(name = "password", columnDefinition = "BINARY(72)", nullable = false)
    private byte[] password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "last_login_ip", length = 45)
    private String lastLoginIp;

    @Column(name = "failed_login_attempts")
    private Integer failedLoginAttempts;

    @Column(name = "account_locked_until")
    private LocalDateTime accountLockedUntil;

    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified = false;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled = true;

    @Column(name = "banned_until")
    private LocalDateTime bannedUntil;

    @Column(name = "banned_reason", columnDefinition = "TINYTEXT")
    private String bannedReason;

    @Column(name = "username", length = 32, nullable = false)
    private String username;

    @Column(name = "sex", length = 16)
    private String sex;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "bio", columnDefinition = "TINYTEXT")
    private String bio;

    @Column(name = "icon_url", length = 2048)
    private String iconUrl;

    @Column(name = "banner_url", length = 2048)
    private String bannerUrl;

    protected User() {}

    public User(
            String id,
            String email,
            byte[] password,
            LocalDateTime lastLoginAt,
            String lastLoginIp,
            Integer failedLoginAttempts,
            LocalDateTime accountLockedUntil,
            Boolean isVerified,
            Boolean isEnabled,
            LocalDateTime bannedUntil,
            String bannedReason,
            String username,
            String sex,
            LocalDate birthDate,
            String bio,
            String iconUrl,
            String bannerUrl) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.lastLoginAt = lastLoginAt;
        this.lastLoginIp = lastLoginIp;
        this.failedLoginAttempts = failedLoginAttempts;
        this.accountLockedUntil = accountLockedUntil;
        this.isVerified = isVerified;
        this.isEnabled = isEnabled;
        this.bannedUntil = bannedUntil;
        this.bannedReason = bannedReason;
        this.username = username;
        this.sex = sex;
        this.birthDate = birthDate;
        this.bio = bio;
        this.iconUrl = iconUrl;
        this.bannerUrl = bannerUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Integer getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(Integer failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public LocalDateTime getAccountLockedUntil() {
        return accountLockedUntil;
    }

    public void setAccountLockedUntil(LocalDateTime accountLockedUntil) {
        this.accountLockedUntil = accountLockedUntil;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public LocalDateTime getBannedUntil() {
        return bannedUntil;
    }

    public void setBannedUntil(LocalDateTime bannedUntil) {
        this.bannedUntil = bannedUntil;
    }

    public String getBannedReason() {
        return bannedReason;
    }

    public void setBannedReason(String bannedReason) {
        this.bannedReason = bannedReason;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
