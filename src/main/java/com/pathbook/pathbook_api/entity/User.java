package com.pathbook.pathbook_api.entity;

import com.pathbook.pathbook_api.dto.UserData;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserData {
    // region Fields

    @Id
    @Column(name = "id", length = 33, nullable = false)
    private String id;

    @Column(name = "email", length = 256, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 72, nullable = false)
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "login_count")
    private Integer loginCount;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "last_login_ip", length = 45)
    private String lastLoginIp;

    @Column(name = "failed_login_attempts")
    private Integer failedLoginAttempts;

    @Column(name = "account_locked_until")
    private LocalDateTime accountLockedUntil;

    @Column(name = "is_verified", nullable = false)
    private Boolean verified = false;

    @Column(name = "is_enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "banned_until")
    private LocalDateTime bannedUntil;

    @Column(name = "banned_reason", columnDefinition = "TINYTEXT")
    private String bannedReason;

    @Column(name = "banned_count")
    private Integer bannedCount;

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

    @OneToMany(mappedBy = "reporter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserReport> userReports = new ArrayList<>();

    @OneToMany(mappedBy = "reportee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserReport> reported = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<PostLike> postLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<PostBookmark> postBookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "reporter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostReport> postReports = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<PostComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<PostLike> commentLikes = new ArrayList<>();

    @OneToMany(mappedBy = "reporter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostReport> commentReports = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pathgroup> pathgroups = new ArrayList<>();

    // endregion

    // region Constructors

    protected User() {}

    public User(String id, String email, String password, String username) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    // endregion

    // region Getters & Setters

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getLoginCount() {
        return loginCount == null ? 0 : loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
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
        return failedLoginAttempts == null ? 0 : failedLoginAttempts;
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

    public Boolean isVerified() {
        return verified;
    }

    public void setVerified(Boolean isVerified) {
        this.verified = isVerified;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean isEnabled) {
        this.enabled = isEnabled;
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

    public Integer getBannedCount() {
        return bannedCount == null ? 0 : bannedCount;
    }

    public void setBannedCount(Integer bannedCount) {
        this.bannedCount = bannedCount;
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

    public List<UserReport> getUserReports() {
        return userReports;
    }

    public List<UserReport> getReported() {
        return reported;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<PostLike> getPostLikes() {
        return postLikes;
    }

    public List<PostBookmark> getPostBookmarks() {
        return postBookmarks;
    }

    public List<PostReport> getPostReports() {
        return postReports;
    }

    public List<PostComment> getComments() {
        return comments;
    }

    public List<PostLike> getCommentLikes() {
        return commentLikes;
    }

    public List<PostReport> getCommentReports() {
        return commentReports;
    }

    public List<Pathgroup> getPathgroups() {
        return pathgroups;
    }

    // endregion

    // region Events

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // endregion

    // region Helper Methods

    /** 로그인 횟수를 증가시킵니다. */
    public void increaseLoginCount() {
        loginCount++;
    }

    /** 로그인 시도 실패 횟수를 초기화하고, 계정 잠금을 해제합니다. */
    public void resetFailedLoginAttempts() {
        failedLoginAttempts = 0;
        accountLockedUntil = null;
    }

    /** 로그인 시도 실패 횟수를 증가시킵니다. */
    public void increaseFailedLoginAttempts() {
        failedLoginAttempts++;
    }

    // endregion
}
