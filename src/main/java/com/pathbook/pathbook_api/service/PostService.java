package com.pathbook.pathbook_api.service;

import com.pathbook.pathbook_api.dto.PostDto;
import com.pathbook.pathbook_api.entity.Post;
import com.pathbook.pathbook_api.entity.PostBookmark;
import com.pathbook.pathbook_api.entity.PostLike;
import com.pathbook.pathbook_api.entity.User;
import com.pathbook.pathbook_api.entity.id.PostBookmarkId;
import com.pathbook.pathbook_api.entity.id.PostLikeId;
import com.pathbook.pathbook_api.exception.PostNotFoundException;
import com.pathbook.pathbook_api.exception.UnauthorizedAccessException;
import com.pathbook.pathbook_api.exception.UserNotFoundException;
import com.pathbook.pathbook_api.repository.PostBookmarkRepository;
import com.pathbook.pathbook_api.repository.PostLikeRepository;
import com.pathbook.pathbook_api.repository.PostRepository;
import com.pathbook.pathbook_api.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {
    @Autowired private UserRepository userRepository;

    @Autowired private PostRepository postRepository;

    @Autowired private PostLikeRepository postLikeRepository;

    @Autowired private PostBookmarkRepository postBookmarkRepository;

    /**
     * 모든 포스트를 불러옵니다.
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<PostDto> getPostList() {
        return postRepository.findAll().stream().map(PostDto::new).toList();
    }

    /**
     * 포스트를 불러옵니다.
     *
     * @param postId
     * @return {@link PostDto} 포스트 데이터
     */
    public PostDto getPost(Long postId) {
        Post post =
                postRepository
                        .findById(postId)
                        .orElseThrow(() -> new PostNotFoundException(postId));

        return new PostDto(post);
    }

    /**
     * 포스트를 새로 작성합니다.
     *
     * @param authorId
     * @param postData
     * @return {@link PostDto} 작성된 포스트
     */
    public PostDto writePost(String authorId, PostDto postData) {
        User author =
                userRepository
                        .findById(authorId)
                        .orElseThrow(() -> UserNotFoundException.withUserId(authorId));

        Post newPost = new Post(author, postData.getTitle(), postData.getContent());

        Post savedPost = postRepository.save(newPost);

        return new PostDto(savedPost);
    }

    /**
     * 포스트를 수정합니다.
     *
     * <p>작성자가 아니라면 수정할 수 없습니다.
     *
     * @param authorId
     * @param postId
     * @param postData
     * @return {@link PostDto} 수정된 포스트
     */
    public PostDto editPost(String authorId, Long postId, PostDto postData) {
        Post post =
                postRepository
                        .findById(postId)
                        .orElseThrow(() -> new PostNotFoundException(postId));

        if (!post.getAuthor().getId().equals(authorId)) {
            throw new UnauthorizedAccessException(
                    String.format("User %s not owning post %d", authorId, postId));
        }

        post.setTitle(postData.getTitle());
        post.setContent(postData.getContent());

        Post editedPost = postRepository.save(post);

        return new PostDto(editedPost);
    }

    /**
     * 포스트를 삭제합니다.
     *
     * <p>작성자가 아니라면 삭제할 수 없습니다.
     *
     * @param authorId
     * @param postId
     */
    public void deletePost(String authorId, Long postId) {
        Post post =
                postRepository
                        .findById(postId)
                        .orElseThrow(() -> new PostNotFoundException(postId));

        if (!post.getAuthor().getId().equals(authorId)) {
            throw new UnauthorizedAccessException(
                    String.format("User %s not owning post %d", authorId, postId));
        }

        postRepository.delete(post);
    }

    @Transactional
    public void addPostLike(String userId, Long postId) {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> UserNotFoundException.withUserId(userId));

        Post post =
                postRepository
                        .findById(postId)
                        .orElseThrow(() -> new PostNotFoundException(postId));

        if (postLikeRepository.existsById(new PostLikeId(userId, postId))) {
            return;
        }

        PostLike like = new PostLike(user, post);

        postLikeRepository.save(like);
    }

    @Transactional
    public void removePostLike(String userId, Long postId) {
        postLikeRepository.deleteById(new PostLikeId(userId, postId));
    }

    @Transactional
    public void addPostBookmark(String userId, Long postId) {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> UserNotFoundException.withUserId(userId));

        Post post =
                postRepository
                        .findById(postId)
                        .orElseThrow(() -> new PostNotFoundException(postId));

        if (postBookmarkRepository.existsById(new PostBookmarkId(userId, postId))) {
            return;
        }

        PostBookmark bookmark = new PostBookmark(user, post);

        postBookmarkRepository.save(bookmark);
    }

    @Transactional
    public void removePostBookmark(String userId, Long postId) {
        postBookmarkRepository.deleteById(new PostBookmarkId(userId, postId));
    }
}
