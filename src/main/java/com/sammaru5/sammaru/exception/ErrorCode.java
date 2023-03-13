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
    COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 댓글은 존재하지 않습니다!"),
    FILE_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 파일은 존재하지 않습니다!"),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 회원은 존재하지 않습니다!"),
    SCHEDULE_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 일정은 존재하지 않습니다!"),
    INDELIBLE_BOARD(HttpStatus.BAD_REQUEST, "해당 게시판은 삭제할 수 없습니다!"),
    WRONG_SEARCH_SUBJECT(HttpStatus.BAD_REQUEST, "올바르지 않은 검색 주제입니다!"),
    VALID_CHECK_FAIL(HttpStatus.BAD_REQUEST, "입력 값에 대한 유효성 검사가 실패했습니다!"),
    NULL_POINTER_EXCEPTION(HttpStatus.BAD_REQUEST, "Null pointer exception 오류가 발생했습니다!"),
    ILLEGAL_ARGUMENT_EXCEPTION(HttpStatus.BAD_REQUEST, "Illegal argument exception 오류가 발생했습니다!"),
    INAPPROPRIATE_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호 규칙에 맞지 않습니다!"),

    // 401 UNAUTHORIZED
    UNAUTHORIZED_USER_ACCESS(HttpStatus.UNAUTHORIZED, "권한이 없는 사용자의 접근입니다!"),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "엑세스 토큰이 유효하지 않습니다!"),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "리프레쉬 토큰이 유효하지 않습니다!"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    TOKEN_WITHOUT_AUTHORITY(HttpStatus.UNAUTHORIZED, "권한이 없는 토큰입니다."),

    // 403 FORBIDDEN
    USER_POINT_CANT_NEGATIVE(HttpStatus.FORBIDDEN, "사용자의 포인트는 음수가 될 수 없습니다!"),
    USER_IS_TEMP_ACCOUNT(HttpStatus.FORBIDDEN, "임시 회원은 관리자가 승인하기 전까지 로그인 할 수 없습니다!"),

    // 404 NOT FOUND
    ARTICLE_NOT_LIKED(HttpStatus.NOT_FOUND, "좋아요를 누르지 않은 게시글입니다"),
    INVALID_VERIFICATION_CODE(HttpStatus.BAD_REQUEST, "유효하지 않는 인증코드 입니다."),

    // 406 NOT ACCEPTABLE
    ACCESS_DENIED(HttpStatus.NOT_ACCEPTABLE, "접근이 거부되었습니다!"),

    // 409 CONFLICT
    ALREADY_EXIST_USER(HttpStatus.CONFLICT, "해당 학번의 사용자가 이미 존재합니다!"),
    ALREADY_EXIST_EMAIL(HttpStatus.CONFLICT, "해당 email 계정의 사용자가 이미 존재합니다!"),
    ALREADY_EXIST_BOARD(HttpStatus.CONFLICT, "해당 게시판이 이미 존재합니다!"),
    ALREADY_LIKED_ARTICLE(HttpStatus.CONFLICT, "이미 좋아요를 누른 게시글입니다!"),
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}