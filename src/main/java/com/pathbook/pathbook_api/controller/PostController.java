package com.pathbook.pathbook_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pathbook.pathbook_api.dto.PostRequest;
import com.pathbook.pathbook_api.dto.PostResponse;
import com.pathbook.pathbook_api.dto.UserPrincipal;
import com.pathbook.pathbook_api.exception.PostNotFoundException;
import com.pathbook.pathbook_api.exception.UnauthorizedAccessException;
import com.pathbook.pathbook_api.service.PostService;

@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/list")
    public ResponseEntity<?> getPostList() {
        List<PostResponse> postList = postService.getPostList();
        return ResponseEntity.ok(postList);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long postId) {
        try {
            PostResponse post = postService.getPost(postId);
            return ResponseEntity.ok(post);
        } catch (PostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @PostMapping("/write")
    public ResponseEntity<?> writePost(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestBody PostRequest request) {
        PostResponse resultPost = postService.writePost(user.getId(), request.title(), request.content());
        return ResponseEntity.status(HttpStatus.CREATED).body(resultPost);
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity<?> updatePost(
            @AuthenticationPrincipal UserPrincipal user,
            @PathVariable Long postId,
            @RequestBody PostRequest request) {
        try {
            PostResponse resultPost = postService.updatePost(postId, user.getId(), request.title(), request.content());
            return ResponseEntity.ok(resultPost);
        } catch (PostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e);
        }
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<?> deletePost(
            @AuthenticationPrincipal UserPrincipal user,
            @PathVariable Long postId) {
        try {
            postService.deletePost(user.getId(), postId);
            return ResponseEntity.ok().build();
        } catch (PostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        } catch (UnauthorizedAccessException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e);
        }
    }

    @PostMapping("/like/{postId}")
    public ResponseEntity<?> likePost(
            @AuthenticationPrincipal UserPrincipal user,
            @PathVariable Long postId) {
        postService.likePost(user.getId(), postId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unlike/{postId}")
    public ResponseEntity<?> unlikePost(
            @AuthenticationPrincipal UserPrincipal user,
            @PathVariable Long postId) {
        postService.unlikePost(user.getId(), postId);
        return ResponseEntity.ok().build();
    }

}
