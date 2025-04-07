package com.pathbook.pathbook_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pathbook.pathbook_api.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    User findByEmail(String email);

    // TODO: DB 커넥션 체크용, 프로덕션에서는 지워야 함.
    @Query(value = "SELECT 1 FROM user LIMIT 1", nativeQuery = true)
    int testTableAccess();

}
