package com.sammaru5.sammaru.service.photo;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.domain.StorageEntity;
import com.sammaru5.sammaru.repository.StorageRepository;
import com.sammaru5.sammaru.service.article.ArticleStatusService;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FileRegisterService {

    private final ArticleStatusService articleStatusService;
    private final StorageRepository storageRepository;

    public StorageEntity addFile(MultipartFile multipartFile, Long articleId) {

        ArticleEntity articleEntity = articleStatusService.findArticleById(articleId).get();

        UUID uid = UUID.randomUUID();
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        File targetFile = new File("src/main/resources/files/" + articleEntity.getBoard().getBoardname() + "/" + uid.toString() + "." + extension);
        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);
        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile); //지움
            e.printStackTrace();
        }

        return storageRepository.save(
                StorageEntity.builder()
                        .article(articleEntity)
                        .filePath(uid.toString() + "." + extension)
                        .fileName(multipartFile.getOriginalFilename())
                        .build()
        );
    }
}
