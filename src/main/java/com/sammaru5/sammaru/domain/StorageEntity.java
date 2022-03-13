package com.sammaru5.sammaru.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "storage")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorageEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private ArticleEntity article;
    private String filePath;
    private String fileName;
}
