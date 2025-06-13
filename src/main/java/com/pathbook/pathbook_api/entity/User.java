package com.pathbook.pathbook_api.entity;

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
public class User {
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

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<UserPostLike> postLikes = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<UserPostBookmark> postBookmarks = new ArrayList<>();
    
    @OneToMany(mappedBy = "reporter", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<UserPostReport> postReports = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<PostComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<UserPostLike> commentLikes = new ArrayList<>();

    @OneToMany(mappedBy = "reporter", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<UserPostReport> commentReports = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = false)
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
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

    public List<Post> getPosts() {
        return posts;
    }
    
    public List<UserPostLike> getPostLikes() {
        return postLikes;
    }
    
    public List<UserPostBookmark> getPostBookmarks() {
        return postBookmarks;
    }

    public List<UserPostReport> getPostReports() {
        return postReports;
    }

    public List<PostComment> getComments() {
        return comments;
    }

    public List<UserPostLike> getCommentLikes() {
        return commentLikes;
    }

    public List<UserPostReport> getCommentReports() {
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

    // endregion
}
