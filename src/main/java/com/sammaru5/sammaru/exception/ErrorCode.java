package com.sammaru5.sammaru.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // UNAUTHORIZED
    UNAUTHORIZED_USER_ACCESS(HttpStatus.UNAUTHORIZED, "권한이 없는 사용자의 접근입니다!"),

    // BAD REQUEST
    ARTICLE_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 articleId 게시글이 존재하지 않습니다!"),
    BOARD_IS_EMPTY(HttpStatus.BAD_REQUEST, "해당 게시판에 게시글이 존재하지 않습니다!"),
    COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 commentId 댓글이 존재하지 않습니다!"),
    FILE_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 파일은 존재하지 않습니다!");

    private final HttpStatus httpStatus;
    private final String detail;
}
