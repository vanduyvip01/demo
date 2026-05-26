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

    // VALIDATION ERROR
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Void>>
    handleValidationException(MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult().getFieldError().getDefaultMessage();

        return ResponseEntity.badRequest().body(ResponseUtils.success(
                        400,
                        message,
                        null));
    }

    // RUNTIME ERROR
    // SERVER ERROR
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Void>>
    handleException(Exception ex)
    {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseUtils.success(
                        500,
                        "Internal server error",
                        null));
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseResponse<Void>>
    handleNotFoundException(NotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND
        ).body(ResponseUtils.success(
                        404,
                        ex.getMessage(),
                        null));
    }
}