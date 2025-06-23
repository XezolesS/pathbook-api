package com.pathbook.pathbook_api.repository;

import com.pathbook.pathbook_api.entity.File;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, String> {}
