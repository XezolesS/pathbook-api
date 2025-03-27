package com.pathbook.pathbook_api;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // 이메일로 사용자 검색
    UserEntity findByEmail(String email);
}
