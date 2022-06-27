package com.sammaru5.sammaru.web.controller.exception;

import com.sammaru5.sammaru.exception.CustomException;
import com.sammaru5.sammaru.web.apiresult.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(CustomException.class)
    public ApiResult<?> handleCustomException(CustomException e) {
        e.printStackTrace();
        return ApiResult.ERROR(e, e.getErrorCode().getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) //@Valid 검사
    public ApiResult<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        e.printStackTrace();
        return ApiResult.ERROR(e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ApiResult<?> handleAccessDeniedException(AccessDeniedException e){
        e.printStackTrace();
        return ApiResult.ERROR(e, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(NullPointerException.class)
    public ApiResult<?> handleNullPointerException(NullPointerException e){
        e.printStackTrace();
        return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResult<?> handleIllegalArgumentException(IllegalArgumentException e){
        e.printStackTrace();
        return ApiResult.ERROR(e, HttpStatus.BAD_REQUEST);
    }
}
