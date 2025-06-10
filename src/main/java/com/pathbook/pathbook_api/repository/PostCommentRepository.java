package com.pathbook.pathbook_api.repository;

import com.pathbook.pathbook_api.entity.Post;
import com.pathbook.pathbook_api.entity.PostComment;
import com.pathbook.pathbook_api.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<PostComment, Integer> {

    List<PostComment> findAllByPost(Post post);

    List<PostComment> findAllByPostId(Integer postId);

    List<PostComment> findAllByAuthor(User author);
}
