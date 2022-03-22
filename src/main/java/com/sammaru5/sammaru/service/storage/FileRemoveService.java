package com.sammaru5.sammaru.service.storage;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.domain.BoardEntity;
import com.sammaru5.sammaru.domain.StorageEntity;
import com.sammaru5.sammaru.exception.NonExistentFileException;
import com.sammaru5.sammaru.repository.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileRemoveService {

    private final StorageRepository storageRepository;

    @Value("${app.fileDir}")
    private String fileDir;

    //해당 파일 삭제
    boolean removeFile(Long boardId, String filePath) throws NonExistentFileException {

        File targetFile = new File(fileDir + boardId + "/" + filePath);
        Optional<StorageEntity> storageEntity = storageRepository.findByFilePath(filePath);
        if(!targetFile.exists() || !storageEntity.isPresent()){
            throw new NonExistentFileException();
        }

        storageRepository.delete(storageEntity.get());
        return targetFile.delete();
    }

    //Article에 속한 모든 파일 삭제
    boolean removeFileByArticle(ArticleEntity articleEntity) throws NonExistentFileException {

        List<StorageEntity> storageEntities = storageRepository.findByArticle(articleEntity);
        for(StorageEntity storageEntity : storageEntities){
            removeFile(articleEntity.getBoard().getId(), storageEntity.getFilePath());
        }
        return true;
    }

}
