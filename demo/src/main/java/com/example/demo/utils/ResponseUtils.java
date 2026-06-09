package com.example.demo.utils;

import com.example.demo.dto.reponse.BaseResponse;

import java.util.UUID;

public class ResponseUtils {

    // OLD VERSION

    public static <T> BaseResponse<T> success(

            int code,

            String message,

            T data

    ) {

        BaseResponse<T> response = new BaseResponse<>();

        response.setCode(code);

        response.setError("SUCCESS");

        response.setMessage(message);

        response.setRequestId(UUID.randomUUID().toString());

        response.setData(data);

        return response;
    }

    // =========================
    // NEW VERSION
    // =========================

    public static <T> BaseResponse<T> success(
            T data
    ) {

        BaseResponse<T> response = new BaseResponse<>();

        response.setCode(20000);

        response.setError("SUCCESS");

        response.setMessage("Success");

        response.setRequestId(UUID.randomUUID().toString());

        response.setData(data);

        return response;
    }

    // =========================
    // ERROR
    // =========================

    public static <T> BaseResponse<T> error(

            int code,

            String error,

            String message

    ) {

        BaseResponse<T> response = new BaseResponse<>();

        response.setCode(code);

        response.setError(error);

        response.setMessage(message);

        response.setRequestId(UUID.randomUUID().toString());

        response.setData(null);

        return response;
    }
}