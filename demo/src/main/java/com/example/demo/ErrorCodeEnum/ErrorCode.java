package com.example.demo.ErrorCodeEnum;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter

public enum ErrorCode {

    // USER

    USER_NOT_FOUND(
            1001,
            HttpStatus.NOT_FOUND,
            "USER_NOT_FOUND"
    ),

    USER_ALREADY_EXISTS(
            1002,
            HttpStatus.BAD_REQUEST,
            "USER_ALREADY_EXISTS"
    ),
    // SYSTEM

    INTERNAL_SERVER_ERROR(
            9999,
            HttpStatus.INTERNAL_SERVER_ERROR,
            "INTERNAL_SERVER_ERROR"
    ),

    VALIDATION_ERROR(
            4000,
            HttpStatus.BAD_REQUEST,
            "VALIDATION_ERROR"
    );

    private final int code;

    private final HttpStatus status;

    private final String messageKey;

    ErrorCode(

            int code,
            HttpStatus status,
            String messageKey
    ) {

        this.code = code;

        this.status = status;

        this.messageKey = messageKey;

    }

}