package com.sammaru5.sammaru.web.dto;

import com.sammaru5.sammaru.domain.ArticleEntity;
<<<<<<< HEAD:src/main/java/com/sammaru5/sammaru/dto/ArticleDTO.java
=======
import lombok.AllArgsConstructor;
>>>>>>> main:src/main/java/com/sammaru5/sammaru/web/dto/ArticleDTO.java
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
public class ArticleDTO {
    private Long id;
    private String title;
    private String content;
    private Timestamp createDt;
    private String author;
    private Integer viewCnt;
    private Integer likeCnt;
    private List<FileDTO> files;

    public ArticleDTO(ArticleEntity articleEntity) {
        copyProperties(articleEntity, this);
        this.createDt = articleEntity.getCreateTime();
        this.author = articleEntity.getUser().getUsername();
        this.viewCnt = articleEntity.getViewCnt();
        this.likeCnt = articleEntity.getViewCnt();
        if(!articleEntity.getFiles().isEmpty()) this.files = articleEntity.getFiles().stream().map(FileDTO::new).collect(Collectors.toList());
    }
}
