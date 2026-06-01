package com.example.demo.exception;

import com.example.demo.dto.reponse.BaseResponse;
import com.example.demo.utils.ResponseUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice

public class GlobalExceptionHandler {
    // VALIDATION EXCEPTION

    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ResponseEntity<BaseResponse<Void>>
    handleValidationException(MethodArgumentNotValidException ex) {

        String message =
                ex.getBindingResult()
                        .getFieldError()
                        .getDefaultMessage();

        return buildResponse(HttpStatus.BAD_REQUEST, message);
    }
    // NOT FOUND EXCEPTION

    @ExceptionHandler(NotFoundException.class)

    public ResponseEntity<BaseResponse<Void>>
    handleNotFoundException(NotFoundException ex) {

        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // BUSINESS EXCEPTION

    @ExceptionHandler(BusinessException.class)

    public ResponseEntity<BaseResponse<Void>>
    handleBusinessException(BusinessException ex) {

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );
    }
    // SYSTEM EXCEPTION

    @ExceptionHandler(SystemException.class)

    public ResponseEntity<BaseResponse<Void>>
    handleSystemException(SystemException ex) {

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage()
        );
    }
    // UNKNOWN EXCEPTION

    @ExceptionHandler(Exception.class)

    public ResponseEntity<BaseResponse<Void>>
    handleException(Exception ex) {

        ex.printStackTrace();
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error"
        );
    }

    // COMMON RESPONSE

    private ResponseEntity<BaseResponse<Void>>
    buildResponse(
            HttpStatus status,
            String message
    ) {
        return ResponseEntity.status(status).body(ResponseUtils.success(status.value(), message, null));

    }

}