package com.pathbook.pathbook_api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pathbook.pathbook_api.entity.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    Optional<Post> findById(Long id);

}
