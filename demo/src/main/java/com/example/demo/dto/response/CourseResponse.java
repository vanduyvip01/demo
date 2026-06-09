package com.example.demo.dto.response;

import com.example.demo.enumtype.CourseStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CourseResponse {
    private Long id;
    private String tittle;
    private String description;
    private CourseStatus status;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
