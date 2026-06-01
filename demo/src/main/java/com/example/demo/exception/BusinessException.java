package com.example.demo.exception;

import com.example.demo.ErrorCodeEnum.ErrorCode;
import lombok.Getter;

@Getter

public class BusinessException
        extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(
            ErrorCode errorCode
    ) {

        this.errorCode = errorCode;

    }

}
