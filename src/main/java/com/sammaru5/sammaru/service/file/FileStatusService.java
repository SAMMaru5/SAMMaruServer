package com.sammaru5.sammaru.service.file;

import com.sammaru5.sammaru.domain.FileEntity;
import com.sammaru5.sammaru.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

@Transactional
@Service @RequiredArgsConstructor
public class FileStatusService {

    private final FileRepository fileRepository;
    @Value("${app.fileDir}")
    private String fileDir;

    public ResponseEntity<InputStreamResource> downloadFile(Long boardId, String filePath) throws IOException {

        Optional<FileEntity> fileEntity = fileRepository.findByFilePath(filePath);

        if(!fileEntity.isPresent()) {
            throw new IOException("해당 파일은 존재하지 않습니다");
        }

        String path = fileDir + boardId + "/" + filePath;
        File file = new File(path);
        HttpHeaders header = new HttpHeaders();

        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileEntity.get().getFileName());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok() .headers(header) .contentLength(file.length()) .contentType(MediaType.parseMediaType("application/octet-stream")) .body(resource);
    }
}
