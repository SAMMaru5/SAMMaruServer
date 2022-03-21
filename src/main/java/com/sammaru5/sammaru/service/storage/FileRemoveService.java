package com.sammaru5.sammaru.service.storage;

import com.sammaru5.sammaru.exception.NonExistentFileException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class FileRemoveService {

    @Value("${app.fileDir}")
    private String fileDir;

    boolean removeFile(Long boardId, String filePath){
        File targetFile = new File(fileDir + boardId + "/" + filePath);
        if(!targetFile.exists()){
            throw new NonExistentFileException();
        }
        return targetFile.delete();
    }
}
