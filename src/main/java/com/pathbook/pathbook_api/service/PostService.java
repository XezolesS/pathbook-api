package com.pathbook.pathbook_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pathbook.pathbook_api.dto.PostResponse;
import com.pathbook.pathbook_api.entity.Post;
import com.pathbook.pathbook_api.entity.PostLike;
import com.pathbook.pathbook_api.exception.PostNotFoundException;
import com.pathbook.pathbook_api.exception.UnauthorizedAccessException;
import com.pathbook.pathbook_api.repository.PostLikeRepository;
import com.pathbook.pathbook_api.repository.PostRepository;
import com.pathbook.pathbook_api.repository.UserRepository;

@Service
public class PostService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;
    @Autowired
    private PostReportService postReportService;

    // TODO: Pagination
    @Transactional(readOnly = true)
    public List<PostResponse> getPostList() {
        List<Post> postList = postRepository.findAll();
        return postList.stream()
                .map(PostResponse::new)
                .toList();
    }

    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        if(postReportService.isPostHidden(postId)) {
            throw new UnauthorizedAccessException("You are not authorized to view this post");
        }

        return new PostResponse(post);
    }

    public PostResponse writePost(String authorId, String title, String content) {
        Post post = new Post(authorId, title, content);
        return new PostResponse(postRepository.save(post));
    }

    @Transactional
    public PostResponse updatePost(Long postId, String authorId, String title, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        if (!post.getAuthorId().equals(authorId)) {
            throw new UnauthorizedAccessException(
                    String.format("User %s has no access to post %d", authorId, postId));
        }

        post.setTitle(title);
        post.setContent(content);

        return new PostResponse(postRepository.save(post));
    }

    @Transactional
    public void deletePost(String authorId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        if (!post.getAuthorId().equals(authorId)) {
            throw new UnauthorizedAccessException(
                    String.format("User %s has no access to post %d", authorId, postId));
        }

        postRepository.deleteById(postId);
    }

    @Transactional
    public void likePost(String userId, Long postId) {
        postLikeRepository.save(new PostLike(
                userRepository.getReferenceById(userId),
                postRepository.getReferenceById(postId)));
    }

    @Transactional
    public void unlikePost(String userId, Long postId) {
        postLikeRepository.deleteByUserIdAndPostId(userId, postId);
    }

}
