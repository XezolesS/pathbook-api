package com.pathbook.pathbook_api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pathbook.pathbook_api.entity.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Long>{
    
    Post findById(long id);

    

}
