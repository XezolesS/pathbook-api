package com.pathbook.pathbook_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "account_lock_status")
public class AccountLockStatus {

    @Id
    @Column(name = "user_id", length = 32, nullable = false)
    private String userId;

    @Column(name = "failed_attempts", nullable = false)
    private int failedAttempts = 0;

    @Column(name = "is_locked", nullable = false)
    private boolean isLocked = false;

    protected AccountLockStatus() {
    }

    public AccountLockStatus(String id) {
        this.userId = id;
        this.failedAttempts = 0;
        this.isLocked = false;
    }

    public String getUserId() {
        return userId;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

}
