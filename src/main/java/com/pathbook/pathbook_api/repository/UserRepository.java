package com.pathbook.pathbook_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pathbook.pathbook_api.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findById(String id);

    User findByEmail(String email);

    User findByVerificationToken(String verificationToken);

    // TODO: DB 커넥션 체크용, 프로덕션에서는 지워야 함.
    @Query(value = "SELECT 1 FROM user LIMIT 1", nativeQuery = true)
    int testTableAccess();

}
