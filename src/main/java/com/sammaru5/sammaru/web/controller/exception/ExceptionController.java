package com.sammaru5.sammaru.web.controller.exception;

import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.exception.ErrorCode;
import com.sammaru5.sammaru.web.apiresult.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e) {
        e.printStackTrace();
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ApiResult.ERROR(e, e.getErrorCode().getHttpStatus()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) //@Valid 검사
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        e.printStackTrace();
        CustomException ce = new CustomException(ErrorCode.VALID_CHECK_FAIL);
        return ResponseEntity.status(ce.getErrorCode().getHttpStatus())
                .body(ApiResult.ERROR(ce, ce.getErrorCode().getHttpStatus()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e){
        e.printStackTrace();
        CustomException ce = new CustomException(ErrorCode.ACCESS_DENIED);
        return ResponseEntity.status(ce.getErrorCode().getHttpStatus())
                .body(ApiResult.ERROR(ce, ce.getErrorCode().getHttpStatus()));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(NullPointerException e){
        e.printStackTrace();
        CustomException ce = new CustomException(ErrorCode.NULL_POINTER_EXCEPTION);
        return ResponseEntity.status(ce.getErrorCode().getHttpStatus())
                .body(ApiResult.ERROR(ce, ce.getErrorCode().getHttpStatus()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e){
        e.printStackTrace();
        CustomException ce = new CustomException(ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION);
        return ResponseEntity.status(ce.getErrorCode().getHttpStatus())
                .body(ApiResult.ERROR(ce, ce.getErrorCode().getHttpStatus()));
    }
}
