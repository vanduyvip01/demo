package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class UserStatsResponse {

    private String gender;

    private long total;

}