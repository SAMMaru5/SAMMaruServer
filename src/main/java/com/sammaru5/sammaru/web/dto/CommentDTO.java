package com.sammaru5.sammaru.web.dto;

import com.sammaru5.sammaru.domain.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentDTO {
    private Long id;
    private String content;

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
    }
}
