package com.pathbook.pathbook_api.controller;

import com.pathbook.pathbook_api.dto.PostCommentDto;
import com.pathbook.pathbook_api.dto.UserPrincipal;
import com.pathbook.pathbook_api.dto.request.PostCommentRequest;
import com.pathbook.pathbook_api.dto.response.PostCommentResponse;
import com.pathbook.pathbook_api.service.PostCommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/post/comment")
public class PostCommentController {
    @Autowired private PostCommentService postCommentService;

    /**
     * 포스트에 달린 댓글을 모두 불러옵니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /post/comment/p/{postId} [GET]}
     *   <li>응답: {@code 200 OK}
     * </ul>
     *
     * @param postId
     * @return
     */
    @GetMapping("/p/{postId}")
    public ResponseEntity<?> getPostComments(@PathVariable Long postId) {
        List<PostCommentDto> postComments = postCommentService.getPostComments(postId);

        return new ResponseEntity<>(postComments, HttpStatus.OK);
    }

    /**
     * 댓글 ID로부터 댓글을 불러옵니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /post/comment/c/{commentId} [GET]}
     *   <li>응답: {@code 200 OK}
     * </ul>
     *
     * @param commentId
     * @return
     */
    @GetMapping("/c/{commentId}")
    public ResponseEntity<?> getPostComment(@PathVariable Long commentId) {
        PostCommentDto postComment = postCommentService.getPostComment(commentId);

        return new ResponseEntity<>(new PostCommentResponse(postComment), HttpStatus.OK);
    }

    /**
     * 댓글을 새로 작성합니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /post/comment/write [POST]}
     *   <li>응답: {@code 200 OK}
     * </ul>
     *
     * @param userPrincipal
     * @param requestBody
     * @return
     */
    @PostMapping("/write")
    public ResponseEntity<?> postWriteComment(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody PostCommentRequest requestBody) {
        PostCommentDto savedPostComment =
                postCommentService.writePostComment(userPrincipal.getId(), requestBody);

        return new ResponseEntity<>(new PostCommentResponse(savedPostComment), HttpStatus.OK);
    }

    /**
     * 댓글을 수정합니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /post/comment/edit [PUT]}
     *   <li>응답: {@code 200 OK}
     * </ul>
     *
     * @param userPrincipal
     * @param requestBody
     * @return
     */
    @PutMapping("/edit")
    public ResponseEntity<?> putEditComment(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody PostCommentRequest requestBody) {
        PostCommentDto editedPostComment =
                postCommentService.editPostComment(userPrincipal.getId(), requestBody);

        return new ResponseEntity<>(new PostCommentResponse(editedPostComment), HttpStatus.OK);
    }

    /**
     * 댓글을 삭제합니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /post/comment/delete/{commentId} [DELETE]}
     *   <li>응답: {@code 204 No Content}
     * </ul>
     *
     * @param userPrincipal
     * @param commentId
     * @return
     */
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<?> deleteDeleteComment(
            @AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long commentId) {
        postCommentService.deletePostComment(userPrincipal.getId(), commentId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
