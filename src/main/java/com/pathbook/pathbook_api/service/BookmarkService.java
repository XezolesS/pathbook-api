package com.pathbook.pathbook_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pathbook.pathbook_api.entity.Bookmark;
import com.pathbook.pathbook_api.entity.Post;
import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.repository.BookmarkRepository;

@Service
public class BookmarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    public Bookmark addBookmark(User user, Post post) {
        Optional<Bookmark> existing = bookmarkRepository.findByUserAndPost(user, post);
        if (existing.isPresent()) {
            return existing.get();
        }

        Bookmark bookmark = new Bookmark(user, post);
        return bookmarkRepository.save(bookmark);
    }

    public void removeBookmark(User user, Post post) {
        bookmarkRepository.deleteByUserAndPost(user, post);
    }

    public List<Bookmark> getBookmarksByUser(User user) {
        return bookmarkRepository.findAllByUser(user);
    }

    public boolean isBookmarked(User user, Post post) {
        return bookmarkRepository.findByUserAndPost(user, post).isPresent();
    }
}
