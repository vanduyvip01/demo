package com.example.demo.ErrorCodeEnum;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    SUCCESS(
            20000,
            "SUCCESS",
            "Success",
            HttpStatus.OK
    ),

    USER_NOT_FOUND(
            40400,
            "USER_NOT_FOUND",
            "User not found",
            HttpStatus.NOT_FOUND
    ),

    VALIDATION_ERROR(
            40000,
            "VALIDATION_ERROR",
            "Validation failed",
            HttpStatus.BAD_REQUEST
    ),

    UNAUTHORIZED(
            40100,
            "UNAUTHORIZED",
            "Invalid username or password",
            HttpStatus.UNAUTHORIZED
    ),

    INTERNAL_SERVER_ERROR(
            50000,
            "INTERNAL_SERVER_ERROR",
            "Internal server error",
            HttpStatus.INTERNAL_SERVER_ERROR
    );

    private final int code;

    private final String error;

    private final String message;

    private final HttpStatus status;

    ErrorCode(
            int code,
            String error,
            String message,
            HttpStatus status
    ) {
        this.code = code;
        this.error = error;
        this.message = message;
        this.status = status;
    }
}






