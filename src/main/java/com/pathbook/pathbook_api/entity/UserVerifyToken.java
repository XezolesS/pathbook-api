package com.pathbook.pathbook_api.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
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
    @Column(name = "token", length = 32, nullable = false, unique = true)
    private String token;
    
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "used", nullable = false)
    private boolean used;

    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    protected UserVerifyToken() {}

    public UserVerifyToken(User user, String token, LocalDateTime expiresAt, boolean used) {
        this.user = user;
        this.token = token;
        this.expiresAt = expiresAt;
        this.used = used;
    }

    public User getUser() {
        return user;
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
