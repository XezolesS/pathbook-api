package com.pathbook.pathbook_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pathbook.pathbook_api.entity.Bookmark;
import com.pathbook.pathbook_api.entity.Post;
import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.repository.PostRepository;
import com.pathbook.pathbook_api.repository.UserRepository;
import com.pathbook.pathbook_api.service.BookmarkService;

@RestController
@RequestMapping("/bookmark")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;
    
    // TODO: 경로에서 {userId}를 제거하고, 세션에서 사용자 정보를 가져오도록 변경.
    // 북마크는 사용자 전용 기능이므로 무조건 로그인 된 상태에서만 사용 가능하므로 위의 방법이 타당함.
    @PostMapping("/add/{userId}/{postId}")
    public ResponseEntity<?> addBookmark(@PathVariable String userId, @PathVariable Long postId) {
        Optional<User> user = findUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("User not found"));
        }

        Optional<Post> post = findPostById(postId);
        if (post.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Post not found"));
        }

        Bookmark bookmark = bookmarkService.addBookmark(user.get(), post.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(bookmark);
    }

    @DeleteMapping("/remove/{userId}/{postId}")
    public ResponseEntity<?> removeBookmark(@PathVariable String userId, @PathVariable Long postId) {
        Optional<User> user = findUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("User not found"));
        }

        Optional<Post> post = findPostById(postId);
        if (post.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Post not found"));
        }

        bookmarkService.removeBookmark(user.get(), post.get());
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

    // TODO: 리팩토링 필요
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
