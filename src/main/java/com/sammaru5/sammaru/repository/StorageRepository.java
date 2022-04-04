package com.sammaru5.sammaru.repository;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.domain.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StorageRepository extends JpaRepository<FileEntity, Long> {

    List<FileEntity> findByArticle(ArticleEntity articleEntity);
    Optional<FileEntity> findByFilePath(String FilePath);
}
