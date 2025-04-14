package com.pathbook.pathbook_api.repository;

import com.pathbook.pathbook_api.entity.AccountLockStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountLockStatusRepository extends JpaRepository<AccountLockStatus, String> {
    AccountLockStatus findByUserId(String userId);
}
