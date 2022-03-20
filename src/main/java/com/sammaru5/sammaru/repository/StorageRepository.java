package com.sammaru5.sammaru.repository;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.domain.StorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StorageRepository extends JpaRepository<StorageEntity, Long> {

    List<StorageEntity> findByArticle(ArticleEntity articleEntity);
}
