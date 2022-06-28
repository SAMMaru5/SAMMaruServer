package com.sammaru5.sammaru.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 400 BAD REQUEST
    ARTICLE_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 게시글은 존재하지 않습니다!"),
    BOARD_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 게시판은 존재하지 않습니다!"),
    BOARD_IS_EMPTY(HttpStatus.BAD_REQUEST, "해당 게시판에 게시글이 존재하지 않습니다!"),
    COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 댓글은 존재하지 않습니다!"),
    FILE_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 파일은 존재하지 않습니다!"),
    BOARD_NOT_REMOVE(HttpStatus.BAD_REQUEST, "해당 게시판은 삭제할 수 없습니다!"),

    // 401 UNAUTHORIZED
    UNAUTHORIZED_USER_ACCESS(HttpStatus.UNAUTHORIZED, "권한이 없는 사용자의 접근입니다!"),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "리프레쉬 토큰이 유효하지 않습니다!"),

    // 403 FORBIDDEN
    USER_POINT_CANT_NEGATIVE(HttpStatus.FORBIDDEN, "사용자의 포인트는 음수가 될 수 없습니다!"),

    // 409 CONFLICT
    ALREADY_EXIST_USER(HttpStatus.CONFLICT, "해당 학번의 사용자가 이미 존재합니다!"),
    ALREADY_EXIST_BOARD(HttpStatus.CONFLICT, "해당 게시판이 이미 존재합니다!"),
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}