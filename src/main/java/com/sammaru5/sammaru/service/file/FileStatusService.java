package com.sammaru5.sammaru.service.file;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.domain.FileEntity;
import com.sammaru5.sammaru.repository.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
public class FileStatusService {
    private final StorageRepository storageRepository;

    public List<FileEntity> findFilesByArticle(ArticleEntity articleEntity) {
        List<FileEntity> findFiles = storageRepository.findByArticle(articleEntity);
        if(findFiles.isEmpty()) {
            return null;
        } else {
            return findFiles;
        }
    }
}
