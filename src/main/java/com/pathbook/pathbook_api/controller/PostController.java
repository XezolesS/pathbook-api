package com.pathbook.pathbook_api.controller;

import com.pathbook.pathbook_api.dto.FetchOption;
import com.pathbook.pathbook_api.dto.PostDto;
import com.pathbook.pathbook_api.dto.PostSortOption;
import com.pathbook.pathbook_api.dto.PostSummaryDto;
import com.pathbook.pathbook_api.dto.UserPrincipal;
import com.pathbook.pathbook_api.dto.request.PostRequest;
import com.pathbook.pathbook_api.dto.response.PostResponse;
import com.pathbook.pathbook_api.dto.response.PostSummaryResponse;
import com.pathbook.pathbook_api.service.PostCommentService;
import com.pathbook.pathbook_api.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/post")
public class PostController {
    @Autowired private PostService postService;

    @Autowired private PostCommentService postCommentService;

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
    public ResponseEntity<?> getPostList(
            @RequestParam(name = "p", required = false) Integer pageParam,
            @RequestParam(name = "s", required = false) Integer sizeParam,
            @RequestParam(name = "sort", required = false) String sortOptionParam) {
        int page = pageParam == null ? 0 : pageParam;
        int size = sizeParam == null ? 10 : sizeParam;
        PostSortOption sortOption = PostSortOption.fromString(sortOptionParam);

        Page<PostSummaryDto> postList = postService.getPostSummaries(page, size, sortOption);

        return new ResponseEntity<>(postList.map(PostSummaryResponse::new), HttpStatus.OK);
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
    public ResponseEntity<?> getPost(
            @PathVariable Long postId,
            @RequestParam(name = "c", required = false) String commentFetchOptionParam,
            @RequestParam(name = "l", required = false) String likeFetchOptionParam,
            @RequestParam(name = "b", required = false) String bookmarkFetchOptionParam) {
        FetchOption commentFetchOption = FetchOption.fromString(commentFetchOptionParam);
        FetchOption likeFetchOption = FetchOption.fromString(likeFetchOptionParam);
        FetchOption bookmarkFetchOption = FetchOption.fromString(bookmarkFetchOptionParam);

        PostDto post =
                postService.getPost(
                        postId, commentFetchOption, likeFetchOption, bookmarkFetchOption);

        if (commentFetchOption == FetchOption.FULL) {
            post.setComments(postCommentService.buildCommentTreeFromDto(post.getComments()));
        }

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
     * @param postRequest
     * @param pathThumbnail
     * @param attachments
     * @return
     */
    @PostMapping(
            value = "/write",
            consumes = {"multipart/form-data"})
    public ResponseEntity<?> postWritePost(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestPart(name = "contents", required = true) PostRequest postRequest,
            @RequestPart(name = "path_thumbnail", required = false) MultipartFile pathThumbnail,
            @RequestPart(name = "attachments", required = false) MultipartFile[] attachments) {
        PostDto savedPost =
                postService.writePost(
                        userPrincipal.getId(), postRequest, pathThumbnail, attachments);

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
    @PutMapping(
            value = "/edit",
            consumes = {"multipart/form-data"})
    public ResponseEntity<?> putEditPost(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestPart(name = "contents", required = true) PostRequest postRequest,
            @RequestPart(name = "path_thumbnail", required = false) MultipartFile pathThumbnail,
            @RequestPart(name = "attachments", required = false) MultipartFile[] attachments) {
        PostDto editedPost =
                postService.editPost(
                        userPrincipal.getId(), postRequest, pathThumbnail, attachments);

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

    /**
     * 포스트에 좋아요를 추가합니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /post/like/{postId} [POST]}
     *   <li>응답: {@code 204 No Content}
     * </ul>
     *
     * @param userPrincipal
     * @param postId
     * @return
     */
    @PostMapping("/like/{postId}")
    public ResponseEntity<?> postLikePost(
            @AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long postId) {
        postService.addPostLike(userPrincipal.getId(), postId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 포스트에 좋아요를 삭제합니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /post/unlike/{postId} [DELETE]}
     *   <li>응답: {@code 204 No Content}
     * </ul>
     *
     * @param userPrincipal
     * @param postId
     * @return
     */
    @DeleteMapping("/unlike/{postId}")
    public ResponseEntity<?> deleteUnlikePost(
            @AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long postId) {
        postService.removePostLike(userPrincipal.getId(), postId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 포스트에 북마크를 추가합니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /post/bookmark/{postId} [POST]}
     *   <li>응답: {@code 204 No Content}
     * </ul>
     *
     * @param userPrincipal
     * @param postId
     * @return
     */
    @PostMapping("/bookmark/{postId}")
    public ResponseEntity<?> postBookmarkPost(
            @AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long postId) {
        postService.addPostBookmark(userPrincipal.getId(), postId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 포스트에 북마크를 삭제합니다.
     *
     * <ul>
     *   <li>엔드포인트: {@code /post/bookmark/{postId} [DELETE]}
     *   <li>응답: {@code 204 No Content}
     * </ul>
     *
     * @param userPrincipal
     * @param postId
     * @return
     */
    @DeleteMapping("/unbookmark/{postId}")
    public ResponseEntity<?> deleteUnbookmarkPost(
            @AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long postId) {
        postService.removePostBookmark(userPrincipal.getId(), postId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
