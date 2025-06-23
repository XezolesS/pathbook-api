// package com.pathbook.pathbook_api.service;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.pathbook.pathbook_api.entity.Bookmark;
// import com.pathbook.pathbook_api.exception.BookmarkAlreadyExistsException;
// import com.pathbook.pathbook_api.exception.BookmarkNotFoundException;
// import com.pathbook.pathbook_api.repository.BookmarkRepository;

// @Service
// public class BookmarkService {
//     @Autowired private BookmarkRepository bookmarkRepository;

//     @Transactional
//     public Bookmark addBookmark(String userId, Long postId) {
//         // TODO: Validate User and Post by Id
//         if (bookmarkRepository.existsByUserIdAndPostId(userId, postId)) {
//             throw new BookmarkAlreadyExistsException(userId, postId);
//         }

//         Bookmark bookmark = new Bookmark(userId, postId);
//         return bookmarkRepository.save(bookmark);
//     }

//     @Transactional
//     public void deleteBookmark(String userId, Long postId) {
//         if (!bookmarkRepository.existsByUserIdAndPostId(userId, postId)) {
//             throw new BookmarkNotFoundException(userId, postId);
//         }

//         bookmarkRepository.deleteByUserIdAndPostId(userId, postId);
//     }

//     public List<Bookmark> getBookmarksByUserId(String userId) {
//         return bookmarkRepository.findAllByUserId(userId);
//     }
// }
