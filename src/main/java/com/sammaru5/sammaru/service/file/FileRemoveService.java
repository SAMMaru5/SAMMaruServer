package com.sammaru5.sammaru.service.file;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.domain.FileEntity;
import com.sammaru5.sammaru.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class FileRemoveService {
    private final FileRepository fileRepository;
    @Value("${app.fileDir}")
    private String fileDir;

    //해당 파일 삭제
    public boolean removeFile(Long boardId, String filePath) throws NullPointerException {

        File targetFile = new File(fileDir + boardId + "/" + filePath);
        Optional<FileEntity> storageEntity = fileRepository.findByFilePath(filePath);
        if(!targetFile.exists() || !storageEntity.isPresent()){
            throw new NullPointerException("해당 파일은 존재하지 않습니다!");
        }

        fileRepository.delete(storageEntity.get());
        return targetFile.delete();
    }

    //Article에 속한 모든 파일 삭제
    public boolean removeFilesByArticle(ArticleEntity articleEntity) throws NullPointerException {

        List<FileEntity> storageEntities = fileRepository.findByArticle(articleEntity);
        for(FileEntity fileEntity : storageEntities){
            removeFile(articleEntity.getBoard().getId(), fileEntity.getFilePath());
        }
        return true;
    }

}
