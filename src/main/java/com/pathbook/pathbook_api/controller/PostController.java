package com.pathbook.pathbook_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pathbook.pathbook_api.entity.Post;
import com.pathbook.pathbook_api.service.PostService;

@Controller
@RequestMapping("/post")
public class PostController {
    
    @Autowired
    private PostService postService;
    
    @GetMapping("/{id}")
    public ResponseEntity<Post> getMethodName(@PathVariable Long id) {
        Post post = postService.getPost(id);

        if (post == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PostMapping("/write")
    public ResponseEntity<Post> postMethodName(@RequestBody Post post) {
        var savedPost = postService.savePost(post);

        return new ResponseEntity<>(savedPost, HttpStatus.OK);
    }

}
