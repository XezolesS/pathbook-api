package com.pathbook.pathbook_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pathbook.pathbook_api.dto.UserPrincipal;
import com.pathbook.pathbook_api.entity.Bookmark;
import com.pathbook.pathbook_api.exception.BookmarkAlreadyExistsException;
import com.pathbook.pathbook_api.exception.BookmarkNotFoundException;
import com.pathbook.pathbook_api.service.BookmarkService;

@RestController
@RequestMapping("/bookmark")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @PostMapping("/add/{postId}")
    public ResponseEntity<?> addBookmark(@AuthenticationPrincipal UserPrincipal user, @PathVariable Long postId) {
        try {
            Bookmark bookmark = bookmarkService.addBookmark(user.getId(), postId);
            return ResponseEntity.status(HttpStatus.CREATED).body(bookmark);
        } catch (BookmarkAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e);
        }
    }

    @DeleteMapping("/remove/{postId}")
    public ResponseEntity<?> removeBookmark(@AuthenticationPrincipal UserPrincipal user, @PathVariable Long postId) {
        try {
            bookmarkService.removeBookmark(user.getId(), postId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Bookmark removed");
        } catch (BookmarkNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e);
        }
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<?> getBookmarks(@PathVariable String userId) {
        List<Bookmark> bookmarks = bookmarkService.getBookmarksByUserId(userId);
        return ResponseEntity.ok(bookmarks);
    }

}
