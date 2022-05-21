package com.sammaru5.sammaru.service.file;

import com.sammaru5.sammaru.domain.FileDB;
import com.sammaru5.sammaru.repository.FileDBRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class FileStorageService {

    private final FileDBRepository fileDBRepository;

    public FileDB store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB fileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
        return fileDBRepository.save(fileDB);
    }

    public FileDB getFile(String id) {
        return fileDBRepository.findById(id).get();
    }

    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }
}
