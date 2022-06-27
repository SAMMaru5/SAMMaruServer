package com.sammaru5.sammaru.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String relatedEntityId;

    public CustomException(ErrorCode errorCode) {
        this(errorCode, null);
    }

    @Override
    public String getMessage() {
        if(relatedEntityId == null) return errorCode.getDetail();
        else return errorCode.getDetail() + ", relatedEntityId: " + relatedEntityId;
    }
}
