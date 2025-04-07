package com.pathbook.pathbook_api.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_verify_token")
public class UserVerifyToken {
    
    @Id
    private String userId;
    private String token;
    private LocalDateTime expiresAt;
    private boolean used;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    protected UserVerifyToken() {}

    public UserVerifyToken(String userId, String token, LocalDateTime expiresAt, boolean used) {
        this.userId = userId;
        this.token = token;
        this.expiresAt = expiresAt;
        this.used = used;
    }

    public String getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }
    
    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

}
