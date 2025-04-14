package com.pathbook.pathbook_api.controller;

import com.pathbook.pathbook_api.entity.Bookmark;
import com.pathbook.pathbook_api.entity.Post;
import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.repository.UserRepository;
import com.pathbook.pathbook_api.repository.PostRepository;
import com.pathbook.pathbook_api.service.BookmarkService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bookmark")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/add/{postId}")
    public ResponseEntity<?> addBookmark(@PathVariable Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Optional<Post> post = findPostById(postId);
        if (post.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Post not found"));
        }

        Bookmark bookmark = bookmarkService.addBookmark(postId);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookmark);
    }

    @DeleteMapping("/remove/{postId}")
    public ResponseEntity<?> removeBookmark(@PathVariable Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Optional<Post> post = findPostById(postId);
        if (post.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Post not found"));
        }

        bookmarkService.removeBookmark(postId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Bookmark removed");
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<?> getBookmarks(@PathVariable String userId) {
        Optional<User> user = findUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("User not found"));
        }

        List<Bookmark> bookmarks = bookmarkService.getBookmarksByUser(user.get());
        return ResponseEntity.ok(bookmarks);
    }

    private Optional<User> findUserById(String userId) {
        return userRepository.findById(userId);
    }

    private Optional<Post> findPostById(Long postId) {
        return postRepository.findById(postId);
    }

    public static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
