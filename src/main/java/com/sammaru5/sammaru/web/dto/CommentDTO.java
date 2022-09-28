package com.sammaru5.sammaru.web.dto;

import com.sammaru5.sammaru.domain.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentDTO {
    private Long id;
    private String content;
    private Timestamp createDt;
    private String author;
    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createDt = comment.getCreateTime();
        this.author =comment.getUser().getUsername();
    }
}
