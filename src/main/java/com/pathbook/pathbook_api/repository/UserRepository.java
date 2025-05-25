package com.pathbook.pathbook_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pathbook.pathbook_api.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

    User findByEmail(String email);

    User findByid(String id);
}
