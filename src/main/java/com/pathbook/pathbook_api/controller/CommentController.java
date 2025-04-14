package com.pathbook.pathbook_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pathbook.pathbook_api.dto.CommentRequest;
import com.pathbook.pathbook_api.entity.Comment;
import com.pathbook.pathbook_api.service.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // TODO: 경로의 모호함 수정.
    // PostController의 매핑이 /post이므로 /comment/post는 모호함. /comment/{postId}로 변경
    // 단 하나의 댓글에 대한 경로는 /comment/{postId}/{commentID}로 설정. (필요시)
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }

    // TODO: Post의 글 추가 리퀘스트와 경로가 같도록 변경 (add, save, write 중 하나로 통일)
    @PostMapping("/add")
    public ResponseEntity<Comment> addComment(@RequestBody CommentRequest request) {
        return ResponseEntity.ok(commentService.addComment(request));
    }

    @PostMapping("/update/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId, @RequestBody CommentRequest request) {
        return ResponseEntity.ok(commentService.updateComment(commentId, request.content()));
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/like/{commentId}")
    public ResponseEntity<Void> likeComment(@PathVariable Long commentId) {
        // TODO: 사용자 한 명당 하나의 좋아요만 가능하도록 변경
        commentService.likeComment(commentId);
        return ResponseEntity.ok().build();
    }

}
