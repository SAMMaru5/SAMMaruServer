package com.sammaru5.sammaru.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File {

    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    private String filePath;
    private String fileName;

    @Builder
    private File (String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    //== 연관관계 메서드 ==//
    public void belongToArticle(Article article) {
        this.article = article;
    }

    public static File createFile(MultipartFile multipartFile, String fileDir, Long boardId) {
        UUID uuid = UUID.randomUUID();
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        java.io.File targetFile = new java.io.File(fileDir + boardId + "/" + uuid + "." + extension);
        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);
        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile); //지움
            e.printStackTrace();
        }

        return File.builder()
                .filePath(uuid + "." + extension)
                .fileName(multipartFile.getOriginalFilename())
                .build();
    }

}
