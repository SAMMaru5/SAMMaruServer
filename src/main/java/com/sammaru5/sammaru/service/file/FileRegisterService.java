package com.sammaru5.sammaru.service.file;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.domain.FileEntity;
import com.sammaru5.sammaru.repository.FileRepository;
import com.sammaru5.sammaru.service.article.ArticleStatusService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service @RequiredArgsConstructor
public class FileRegisterService {
    private final ArticleStatusService articleStatusService;
    private final FileRepository fileRepository;
    @Value("${app.fileDir}")
    private String fileDir;

    public boolean addFiles(MultipartFile[] multipartFiles, Long articleId) {

        ArticleEntity articleEntity = articleStatusService.findArticle(articleId).get();

        for(MultipartFile multipartFile : multipartFiles){
            UUID uid = UUID.randomUUID();
            String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            File targetFile = new File(fileDir + articleEntity.getBoard().getId() + "/" + uid.toString() + "." + extension);
            try {
                InputStream fileStream = multipartFile.getInputStream();
                FileUtils.copyInputStreamToFile(fileStream, targetFile);
            } catch (IOException e) {
                FileUtils.deleteQuietly(targetFile); //지움
                e.printStackTrace();
            }
            fileRepository.save(
                    FileEntity.builder()
                            .article(articleEntity)
                            .filePath(uid.toString() + "." + extension)
                            .fileName(multipartFile.getOriginalFilename())
                            .build()
            );
        }

        return true;
    }
}
