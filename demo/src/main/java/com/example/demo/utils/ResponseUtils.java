package com.example.demo.utils;

import com.example.demo.dto.reponse.BaseResponse;

import java.util.UUID;

public class ResponseUtils {

    public static <T> BaseResponse<T> success(
            int code,
            String message,
            T data
    ) {

        BaseResponse<T> response = new BaseResponse<>();

        response.setCode(code);
        response.setMessage(message);
        response.setRequestId(UUID.randomUUID().toString()
        );
        response.setData(data);

        return response;
    }
}