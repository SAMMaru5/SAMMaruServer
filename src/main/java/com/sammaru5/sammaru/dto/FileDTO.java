package com.sammaru5.sammaru.dto;

import com.sammaru5.sammaru.domain.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static org.springframework.beans.BeanUtils.copyProperties;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO {
    private String filePath;
    private String fileName;

    public FileDTO(FileEntity fileEntity){
        copyProperties(fileEntity, this);
    }
}
