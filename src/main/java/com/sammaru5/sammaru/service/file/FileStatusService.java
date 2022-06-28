package com.sammaru5.sammaru.service.file;

import com.sammaru5.sammaru.domain.File;
import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

@Transactional
@Service @RequiredArgsConstructor
public class FileStatusService {

    private final FileRepository fileRepository;
    @Value("${app.fileDir}")
    private String fileDir;

    public ResponseEntity<InputStreamResource> downloadFile(Long boardId, String filePath) throws CustomException, FileNotFoundException {

        Optional<File> fileEntity = fileRepository.findByFilePath(filePath);

        if(!fileEntity.isPresent()) {
            throw new CustomException(ErrorCode.FILE_NOT_FOUND, filePath);
        }

        String path = fileDir + boardId + "/" + filePath;
        java.io.File file = new java.io.File(path);
        HttpHeaders header = new HttpHeaders();

        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileEntity.get().getFileName());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok() .headers(header) .contentLength(file.length()) .contentType(MediaType.parseMediaType("application/octet-stream")) .body(resource);
    }
}
