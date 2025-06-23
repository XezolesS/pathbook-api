package com.pathbook.pathbook_api.dto;

import com.pathbook.pathbook_api.entity.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {
    // region Fields

    private String id;
    private String email;
    private String password;
    private LocalDateTime deletedAt;
    private LocalDateTime accountLockedUntil;
    private boolean verified;
    private boolean enabled;
    private LocalDateTime bannedUntil;
    private String bannedReason;

    // endregion

    // region Constructors

    public UserPrincipal(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.deletedAt = user.getDeletedAt();
        this.accountLockedUntil = user.getAccountLockedUntil();
        this.verified = user.isVerified();
        this.enabled = user.isEnabled();
        this.bannedUntil = user.getBannedUntil();
        this.bannedReason = user.getBannedReason();
    }

    // endregion

    // region Getters
    public String getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public LocalDateTime getAccountLockedUntil() {
        return accountLockedUntil;
    }

    public boolean isVerified() {
        return verified;
    }

    public LocalDateTime getBannedUntil() {
        return bannedUntil;
    }

    public String getBannedReason() {
        return bannedReason;
    }

    // endregion

    // region Account Auths Overrides

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        LocalDateTime now = LocalDateTime.now();

        if (accountLockedUntil != null && now.isBefore(accountLockedUntil)) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !isDeleted() && enabled && verified && !isBanned();
    }

    // endregion

    // region Helper Methods

    public boolean isDeleted() {
        return deletedAt != null && LocalDateTime.now().isAfter(deletedAt);
    }

    public boolean isBanned() {
        return bannedUntil != null && LocalDateTime.now().isBefore(bannedUntil);
    }

    public AccountStatus getAccountStatus() {
        if (isDeleted()) {
            return AccountStatus.DELETED;
        }

        if (!enabled) {
            return AccountStatus.DISABLED;
        }

        if (isBanned()) {
            return AccountStatus.BANNED;
        }

        if (!isAccountNonLocked()) {
            return AccountStatus.LOCKED;
        }

        if (!verified) {
            return AccountStatus.UNVERIFIED;
        }

        return AccountStatus.ACTIVE;
    }

    public boolean isAccountLocked() {
        return accountLockedUntil != null && accountLockedUntil.isBefore(LocalDateTime.now());
    }

    // endregion
}
