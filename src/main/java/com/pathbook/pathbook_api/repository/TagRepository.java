package com.pathbook.pathbook_api.repository;

import com.pathbook.pathbook_api.entity.Tag;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
}
