package com.sammaru5.sammaru.repository;

import com.sammaru5.sammaru.domain.Article;
import com.sammaru5.sammaru.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findByArticle(Article article);
    Optional<File> findByFilePath(String FilePath);
}
