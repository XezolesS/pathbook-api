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
import com.pathbook.pathbook_api.repository.PostBookmarkRepository;
import com.pathbook.pathbook_api.repository.PostLikeRepository;
import com.pathbook.pathbook_api.repository.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {
    @Autowired private UserService userService;

    @Autowired private PostRepository postRepository;

    @Autowired private PostLikeRepository postLikeRepository;

    @Autowired private PostBookmarkRepository postBookmarkRepository;

    /**
     * 포스트 ID로부터 포스트 엔티티를 불러옵니다.
     *
     * @param postId
     * @return {@link Post}
     */
    public Post fromPostId(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
    }

    /**
     * 포스트 ID로부터 포스트 존재 여부를 확인합니다.
     *
     * @param postId
     * @return
     */
    public boolean existsByPostId(Long postId) {
        return postRepository.existsById(postId);
    }

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
        Post post = fromPostId(postId);

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
        User author = userService.fromUserId(authorId);
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
     * @param postData
     * @return {@link PostDto} 수정된 포스트
     */
    public PostDto editPost(String authorId, PostDto postData) {
        Post post = fromPostId(postData.getId());

        if (!post.getAuthor().getId().equals(authorId)) {
            throw new UnauthorizedAccessException(
                    String.format("User %s not owning post %d", authorId, postData.getId()));
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
        Post post = fromPostId(postId);

        if (!post.getAuthor().getId().equals(authorId)) {
            throw new UnauthorizedAccessException(
                    String.format("User %s not owning post %d", authorId, postId));
        }

        postRepository.delete(post);
    }

    /**
     * 포스트에 좋아요를 추가합니다.
     *
     * @param userId
     * @param postId
     */
    @Transactional
    public void addPostLike(String userId, Long postId) {
        User user = userService.fromUserId(userId);
        Post post = fromPostId(postId);

        if (postLikeRepository.existsById(new PostLikeId(userId, postId))) {
            return;
        }

        PostLike like = new PostLike(user, post);

        postLikeRepository.save(like);
    }

    /**
     * 포스트에 좋아요를 삭제합니다.
     *
     * <p>없는 경우 무시합니다.
     *
     * @param userId
     * @param postId
     */
    @Transactional
    public void removePostLike(String userId, Long postId) {
        postLikeRepository.deleteById(new PostLikeId(userId, postId));
    }

    /**
     * 포스트에 북마크를 추가합니다.
     *
     * @param userId
     * @param postId
     */
    @Transactional
    public void addPostBookmark(String userId, Long postId) {
        User user = userService.fromUserId(userId);
        Post post = fromPostId(postId);

        if (postBookmarkRepository.existsById(new PostBookmarkId(userId, postId))) {
            return;
        }

        PostBookmark bookmark = new PostBookmark(user, post);

        postBookmarkRepository.save(bookmark);
    }

    /**
     * 포스트에 북마크를 삭제합니다.
     *
     * <p>없는 경우 무시합니다.
     *
     * @param userId
     * @param postId
     */
    @Transactional
    public void removePostBookmark(String userId, Long postId) {
        postBookmarkRepository.deleteById(new PostBookmarkId(userId, postId));
    }
}
