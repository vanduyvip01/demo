package com.example.demo.exception;

import com.example.demo.dto.reponse.BaseResponse;
import com.example.demo.utils.ResponseUtils;
import com.example.demo.ErrorCodeEnum.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BaseResponse<Object>> handleBusinessException(BusinessException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        String key = errorCode.name(); // Lấy "USER_NOT_FOUND", "INTERNAL_SERVER_ERROR",...

        String translatedMessage = getLocalizedMessage(key, errorCode.getMessage());

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ResponseUtils.error(
                        errorCode.getCode(),
                        key,
                        translatedMessage
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> handleValidation(MethodArgumentNotValidException ex) {
        String errorKey = ex.getBindingResult().getFieldError().getDefaultMessage();
        String translatedMessage = getLocalizedMessage(errorKey, "Validation failed");

        return ResponseEntity.badRequest().body(
                ResponseUtils.error(40000, "VALIDATION_ERROR", translatedMessage)
        );
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleException(Exception ex) {
        ex.printStackTrace();
        String translatedMessage = getLocalizedMessage("INTERNAL_SERVER_ERROR", "Internal server error");

        return ResponseEntity.internalServerError().body(
                ResponseUtils.error(50000, "INTERNAL_SERVER_ERROR", translatedMessage)
        );
    }


    private String getLocalizedMessage(String key, String defaultMsg) {
        try {
            return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return defaultMsg;
        }
    }
}