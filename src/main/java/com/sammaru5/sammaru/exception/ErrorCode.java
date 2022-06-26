package com.sammaru5.sammaru.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // UNAUTHORIZED
    ARTICLE_UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "해당 게시글에 권한이 없는 사용자입니다"),
    COMMENT_UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "해당 댓글에 권한이 없는 사용자입니다"),

    // BAD REQUEST
    ARTICLE_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 articleId 게시글이 존재하지 않습니다!"),
    BOARD_EMPTY(HttpStatus.BAD_REQUEST, "해당 게시판에 게시글이 존재하지 않습니다!"),
    COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 commentId 댓글이 존재하지 않습니다!"),

    private final HttpStatus httpStatus;
    private final String detail;
}
