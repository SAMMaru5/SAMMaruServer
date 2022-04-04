package com.sammaru5.sammaru.service.file;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.domain.StorageEntity;
import com.sammaru5.sammaru.repository.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class FileSearchService {

    private final StorageRepository storageRepository;

    public List<StorageEntity> findFilesByArticle(ArticleEntity articleEntity) {
        List<StorageEntity> findFiles = storageRepository.findByArticle(articleEntity);
        if(findFiles.isEmpty()) {
            return null;
        } else {
            return findFiles;
        }
    }
}
