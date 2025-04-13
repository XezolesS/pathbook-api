package com.pathbook.pathbook_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pathbook.pathbook_api.entity.Post;
import com.pathbook.pathbook_api.repository.PostRepository;

import java.util.Optional;

@Service
public class PostService {
    
    @Autowired
    private PostRepository postRepository;

    public Post getPost(long id) {
        Optional<Post> post = postRepository.findById(id);

        return post.orElse(null);
    }

    public Post savePost(Post post) {
        Post savedPost = postRepository.save(post);

        return savedPost;
    }

    public void deletePost(long id) {
        postRepository.deleteById(id);
    }

}
