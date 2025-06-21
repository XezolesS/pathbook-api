package com.pathbook.pathbook_api.controller;

import com.pathbook.pathbook_api.dto.PostDto;
import com.pathbook.pathbook_api.dto.UserPrincipal;
import com.pathbook.pathbook_api.dto.request.PostRequest;
import com.pathbook.pathbook_api.dto.response.PostResponse;
import com.pathbook.pathbook_api.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/post")
public class PostController {
    @Autowired private PostService postService;

    /**
     * 포스트를 여러개 불러옵니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /post/list [GET]}
     *   <li>응답: {@code 200 OK}
     * </ul>
     *
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<?> getPostList() {
        List<PostDto> postList = postService.getPostList();

        return new ResponseEntity<>(
                postList.stream().map(PostResponse::new).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    /**
     * 포스트를 불러옵니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /post/p/{postId} [GET]}
     *   <li>응답: {@code 200 OK}
     * </ul>
     *
     * @param postId
     * @return
     */
    @GetMapping("/p/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long postId) {
        PostDto post = postService.getPost(postId);

        return new ResponseEntity<>(new PostResponse(post), HttpStatus.OK);
    }

    /**
     * 포스트를 새로 작성합니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /post/write [POST]}
     *   <li>응답: {@code 201 Created}
     * </ul>
     *
     * @param userPrincipal
     * @param requestBody
     * @return
     */
    @PostMapping("/write")
    public ResponseEntity<?> postWritePost(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody PostRequest requestBody) {
        PostDto savedPost = postService.writePost(userPrincipal.getId(), requestBody);

        return new ResponseEntity<>(new PostResponse(savedPost), HttpStatus.CREATED);
    }

    /**
     * 포스트를 수정합니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /post/edit/{postId} [PUT]}
     *   <li>응답: {@code 200 OK}
     * </ul>
     *
     * @param userPrincipal
     * @param postId
     * @param requestBody
     * @return
     */
    @PutMapping("/edit/{postId}")
    public ResponseEntity<?> putEditPost(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long postId,
            @RequestBody PostRequest requestBody) {
        PostDto editedPost = postService.editPost(userPrincipal.getId(), postId, requestBody);

        return new ResponseEntity<>(new PostResponse(editedPost), HttpStatus.OK);
    }

    /**
     * 포스트를 삭제합니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /post/delete/{postId} [DELETE]}
     *   <li>응답: {@code 204 No Content}
     * </ul>
     *
     * @param userPrincipal
     * @param postId
     * @return
     */
    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<?> deleteDeletePost(
            @AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long postId) {
        postService.deletePost(userPrincipal.getId(), postId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
