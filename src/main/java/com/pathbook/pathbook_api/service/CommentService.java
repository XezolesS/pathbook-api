// package com.pathbook.pathbook_api.service;

// import com.pathbook.pathbook_api.entity.Comment;
// import com.pathbook.pathbook_api.entity.CommentLike;
// import com.pathbook.pathbook_api.exception.CommentNotFoundException;
// import com.pathbook.pathbook_api.exception.UnauthorizedAccessException;
// import com.pathbook.pathbook_api.repository.CommentLikeRepository;
// import com.pathbook.pathbook_api.repository.CommentRepository;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import java.util.List;

// @Service
// public class CommentService {
//     @Autowired private CommentRepository commentRepository;

//     @Autowired private CommentLikeRepository commentLikeRepository;

//     @Autowired private CommentReportService commentReportService;

//     public List<Comment> getCommentListByPostId(Long postId) {
//         // TODO: postId Validation
//         return commentRepository.findAllByPostId(postId);
//     }

//     public Comment writeComment(String authorId, Long postId, String content) {
//         Comment comment = new Comment(authorId, postId, content);
//         return commentRepository.save(comment);
//     }

//     @Transactional
//     public Comment updateComment(Long commentId, String authorId, String newContent) {
//         Comment comment =
//                 commentRepository
//                         .findById(commentId)
//                         .orElseThrow(() -> new CommentNotFoundException(commentId));

//         if (!comment.getAuthorId().equals(authorId)) {
//             throw new UnauthorizedAccessException(
//                     String.format("User %s has no access to comment %d", authorId, commentId));
//         }

//         comment.setContent(newContent);

//         return commentRepository.save(comment);
//     }

//     @Transactional
//     public void deleteComment(String authorId, Long commentId) {
//         Comment comment =
//                 commentRepository
//                         .findById(commentId)
//                         .orElseThrow(() -> new CommentNotFoundException(commentId));

//         if (!comment.getAuthorId().equals(authorId)) {
//             throw new UnauthorizedAccessException(
//                     String.format("User %s has no access to comment %d", authorId, commentId));
//         }

//         commentRepository.deleteById(commentId);
//     }

//     public void likeComment(String userId, Long commentId) {
//         CommentLike commentLike = new CommentLike(userId, commentId);
//         commentLikeRepository.save(commentLike);
//     }

//     @Transactional
//     public void unlikeComment(String userId, Long commentId) {
//         commentLikeRepository.deleteByUserIdAndCommentId(userId, commentId);
//     }
// }
