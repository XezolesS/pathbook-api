package com.pathbook.pathbook_api.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {
    
    @Id
    @Column(name = "id", length = 32, nullable = false)
    private String id;
    
    @Column(name = "username", length = 32, nullable = false)
    private String username;
    
    @Column(name = "email", length = 320, nullable = false, unique = true)
    private String email;
    
    @Column(name = "password", length = 255, nullable = false)
    private String password;
    
    @Column(name = "verified")
    private boolean verified;
    
    // TODO: 아이콘과 배너는 DB가 아닌 스토리지에 저장하는 것이 더 좋음.
    // 추후 컬럼은 Path로 변경하고, 파일 시스템을 구현!
    @Lob
    @Column(name = "icon", length = 16 * 1024 * 1024)
    private byte[] icon;
    
    @Column(name = "icon_content_type")
    private String iconContentType;
    
    @Lob
    @Column(name = "banner", length = 16 * 1024 * 1024)
    private byte[] banner;

    @Column(name = "banner_content_type")
    private String bannerContentType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLike> likedPosts = new ArrayList<>();

    protected User() {
    }

    public User(String id, String username, String email, String password, boolean verified) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.verified = verified;
    }

    public String getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isVerified() {
        return this.verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public String getIconContentType() {
        return iconContentType;
    }

    public void setIconContentType(String iconContentType) {
        this.iconContentType = iconContentType;
    }

    public byte[] getBanner() {
        return banner;
    }

    public void setBanner(byte[] banner) {
        this.banner = banner;
    }

    public String getBannerContentType() {
        return bannerContentType;
    }

    public void setBannerContentType(String bannerContentType) {
        this.bannerContentType = bannerContentType;
    }

}
