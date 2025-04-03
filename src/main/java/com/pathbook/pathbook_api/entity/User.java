package com.pathbook.pathbook_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "user")
public class User {

    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    private boolean verified;
    private String verificationToken;

    protected User() {}

    public User(String id, String username, String email, String password, boolean verified, String verificationToken) {
        this.id = id != null ? id : UUID.randomUUID().toString(); // id가 없으면 자동 생성
        this.username = username;
        this.email = email;
        this.password = password;
        this.verified = verified;
        this.verificationToken = verificationToken != null ? verificationToken : UUID.randomUUID().toString(); // 토큰 자동 생성
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

    public String getVerificationToken() {
        return this.verificationToken;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

}
