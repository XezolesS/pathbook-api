package com.pathbook.pathbook_api.repository;

import com.pathbook.pathbook_api.entity.Post;
import com.pathbook.pathbook_api.entity.PostAttachment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostAttachmentRepository extends JpaRepository<PostAttachment, Integer> {
    List<PostAttachment> findAllByPost(Post post);
}
