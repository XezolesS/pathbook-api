package com.pathbook.pathbook_api.repository;

import com.pathbook.pathbook_api.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
}
