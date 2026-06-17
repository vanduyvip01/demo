package com.example.demo.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class EnrollmentResponse {
    private Long id;
    private Long userId;
    private long courseId;
    private String courseTitle;
    private LocalDateTime enrolledAt;
}
