package com.pathbook.pathbook_api.service;

import com.pathbook.pathbook_api.entity.Bookmark;
import com.pathbook.pathbook_api.entity.Post;
import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.repository.BookmarkRepository;
import com.pathbook.pathbook_api.repository.PostRepository;
import com.pathbook.pathbook_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookmarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Transactional
    public Bookmark addBookmark(Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        Optional<Bookmark> existing = bookmarkRepository.findByUserAndPost(user, post);
        if (existing.isPresent()) {
            return existing.get();
        }

        Bookmark bookmark = new Bookmark(user, post);
        return bookmarkRepository.save(bookmark);
    }

    @Transactional
    public void removeBookmark(Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        bookmarkRepository.deleteByUserAndPost(user, post);
    }

    public List<Bookmark> getBookmarksByUser(User user) {
        return bookmarkRepository.findAllByUser(user);
    }

    public boolean isBookmarked(Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        return bookmarkRepository.findByUserAndPost(user, post).isPresent();
    }
}