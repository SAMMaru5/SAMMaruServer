package com.sammaru5.sammaru.dto;

import com.sammaru5.sammaru.domain.ArticleEntity;
import com.sammaru5.sammaru.domain.StorageEntity;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;

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
    private List<String> filePath;
    private List<String> fileName;

    public ArticleDTO(ArticleEntity articleEntity) {
        copyProperties(articleEntity, this);
        this.createDt = articleEntity.getCreateTime();
        this.author = articleEntity.getUser().getUsername();
    }

    public ArticleDTO(ArticleEntity articleEntity, List<StorageEntity> files) {
        this(articleEntity);

        if(files != null) {
            for(int i=0,size=files.size();i<size;i++) {
                this.filePath.add(files.get(i).getFilePath());
                this.fileName.add(files.get(i).getFileName());
            }
        } else {
            this.filePath = null;
            this.fileName = null;
        }
    }
}
