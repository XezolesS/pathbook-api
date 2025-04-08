package com.pathbook.pathbook_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pathbook.pathbook_api.entity.UserVerifyToken;

@Repository
public interface UserVerifyTokenRepository extends JpaRepository<UserVerifyToken, String> {

}
