// package com.pathbook.pathbook_api.service;

// import com.pathbook.pathbook_api.entity.Post;
// import com.pathbook.pathbook_api.entity.User;
// import com.pathbook.pathbook_api.exception.PostNotFoundException;
// import com.pathbook.pathbook_api.exception.UnauthorizedAccessException;
// import com.pathbook.pathbook_api.exception.UserNotFoundException;
// import com.pathbook.pathbook_api.repository.PostLikeRepository;
// import com.pathbook.pathbook_api.repository.PostRepository;
// import com.pathbook.pathbook_api.repository.UserRepository;
// import com.pathbook.pathbook_api.response.PostResponse;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import java.util.List;

// @Service
// public class PostService {
//     @Autowired private UserRepository userRepository;

//     @Autowired private PostRepository postRepository;

//     @Autowired private PostLikeRepository postLikeRepository;

//     @Autowired private PostReportService postReportService;

//     @Transactional(readOnly = true)
//     public List<PostResponse> getPostList() {
//         return postRepository.findAll().stream()
//                 .map(PostResponse::new)
//                 .toList();
//     }

//     @Transactional(readOnly = true)
//     public PostResponse getPost(Integer postId) {
//         Post post = postRepository.findById(postId)
//                 .orElseThrow(() -> new PostNotFoundException(postId));
//         return new PostResponse(post);
//     }

//     @Transactional
//     public PostResponse createPost(String authorId, String title, String content) {
//         User author = userRepository.findById(authorId)
//                 .orElseThrow(() -> new UserNotFoundException(authorId));
//         Post post = new Post(author, title, content);
//         return new PostResponse(postRepository.save(post));
//     }

//     @Transactional
//     public PostResponse updatePost(Integer postId, String authorId, String title, String content) {
//         Post post = postRepository.findById(postId)
//                 .orElseThrow(() -> new PostNotFoundException(postId));

//         if (!post.getAuthor().getId().equals(authorId)) {
//             throw new UnauthorizedAccessException("게시글 수정 권한이 없습니다");
//         }

//         post.setTitle(title);
//         post.setContent(content);
//         post.setUpdatedAt(java.time.LocalDateTime.now());

//         return new PostResponse(postRepository.save(post));
//     }

//     @Transactional
//     public void deletePost(String authorId, Integer postId) {
//         Post post = postRepository.findById(postId)
//                 .orElseThrow(() -> new PostNotFoundException(postId));

//         if (!post.getAuthor().getId().equals(authorId)) {
//             throw new UnauthorizedAccessException("게시글 삭제 권한이 없습니다");
//         }

//         postRepository.delete(post);
//     }

//     @Transactional
//     public void likePost(String userId, Integer postId) {
//         User user = userRepository.findById(userId)
//                 .orElseThrow(() -> new UserNotFoundException(userId));
//         Post post = postRepository.findById(postId)
//                 .orElseThrow(() -> new PostNotFoundException(postId));
//         postLikeRepository.save(new PostLike(user, post));
//     }

//     @Transactional
//     public void unlikePost(String userId, Integer postId) {
//         User user = userRepository.findById(userId)
//                 .orElseThrow(() -> new UserNotFoundException(userId));
//         Post post = postRepository.findById(postId)
//                 .orElseThrow(() -> new PostNotFoundException(postId));
//         postLikeRepository.deleteByUserAndPost(user, post);
//     }
// }
