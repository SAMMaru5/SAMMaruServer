package com.sammaru5.sammaru.web.dto;

import com.sammaru5.sammaru.domain.File;
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

    public FileDTO(File file){
        copyProperties(file, this);
    }
}
