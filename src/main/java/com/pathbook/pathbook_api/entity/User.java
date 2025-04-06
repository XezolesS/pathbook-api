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

    protected User() {}

    public User(String id, String username, String email, String password, boolean verified) {
        this.id = id != null ? id : UUID.randomUUID().toString(); // id가 없으면 자동 생성
        this.username = username;
        this.email = email;
        this.password = password;
        this.verified = verified;
    }

    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public boolean isVerified() { return verified; }

    public void setVerified(boolean verified) { this.verified = verified; }
}
