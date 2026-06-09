package com.example.demo.dto.response;

import lombok.Data;

@Data
public class LessonResponse {
    private Long id;
    private Long courseId;
    private String tittle;
    private String content;
    private int position;
}
