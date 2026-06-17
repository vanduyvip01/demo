package com.example.demo.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class LessonUpdateRequest {
    @NotBlank(message = "Tiêu đề bài học không được trống")
    private String tittle;

    @NotBlank(message = "Nội dung không được để trống")
    private String content;

    @NotNull(message = "Vị trí bài học không được để trống")
    @Positive(message = "Vị trí phải là số dương lớn hơn 0")
    private Integer position;
}
