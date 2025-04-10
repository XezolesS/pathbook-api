package com.pathbook.pathbook_api.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    protected User() {}

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

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean isVerified() {
        return this.verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
