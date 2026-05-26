package com.example.demo.dto.reponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse<T> {

    private int code;

    private String message;

    private String requestId;

    private T data;
}