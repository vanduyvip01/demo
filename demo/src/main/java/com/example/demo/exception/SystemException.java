package com.example.demo.exception;

import com.example.demo.ErrorCodeEnum.ErrorCode;
import lombok.Getter;

@Getter

public class SystemException extends RuntimeException {

    private final ErrorCode errorCode;

    public SystemException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
