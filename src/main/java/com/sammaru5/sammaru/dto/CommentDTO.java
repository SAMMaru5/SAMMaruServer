package com.sammaru5.sammaru.dto;

import com.sammaru5.sammaru.domain.CommentEntity;

public class CommentDTO {
    private Long id;
    private String content;

    public CommentDTO(CommentEntity comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
    }
}
