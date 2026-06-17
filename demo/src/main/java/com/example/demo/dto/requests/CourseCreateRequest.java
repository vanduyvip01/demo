package com.example.demo.dto.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.math.BigDecimal;

@Data
public class CourseCreateRequest {
    @NotBlank(message = "Tieu de khong duoc de trong")
    private String tittle;

    private String description;

    @NotNull(message = "Gia tien khong duoc de trong")
    @Min(value = 0, message = "Gia tien khong duoc nho hon 0")
    private BigDecimal price;
}
