package com.sammaru5.sammaru.service.file;

import com.sammaru5.sammaru.domain.Article;
import com.sammaru5.sammaru.domain.File;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service @RequiredArgsConstructor
public class FileRemoveService {
    private final FileRepository fileRepository;
    @Value("${app.fileDir}")
    private String fileDir;

    //해당 파일 삭제
    public boolean removeFile(Long boardId, String filePath) throws CustomException {

        java.io.File targetFile = new java.io.File(fileDir + boardId + "/" + filePath);
        Optional<File> storageEntity = fileRepository.findByFilePath(filePath);
        if(!targetFile.exists() || !storageEntity.isPresent()){
            throw new CustomException(ErrorCode.FILE_NOT_FOUND, filePath);
        }

        fileRepository.delete(storageEntity.get());
        return targetFile.delete();
    }

    //Article에 속한 모든 파일 삭제
    public boolean removeFilesByArticle(Article article) throws CustomException {

        List<File> storageEntities = fileRepository.findByArticle(article);
        for(File file : storageEntities){
            removeFile(article.getBoard().getId(), file.getFilePath());
        }
        return true;
    }

}
