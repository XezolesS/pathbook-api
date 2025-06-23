package com.pathbook.pathbook_api.repository;

import com.pathbook.pathbook_api.entity.PostAttachment;
import com.pathbook.pathbook_api.entity.id.PostAttachmentId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostAttachmentRepository extends JpaRepository<PostAttachment, PostAttachmentId> {}
