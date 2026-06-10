package com.example.demo.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LessonCreateRequest {

    @NotBlank(message = "Tieu de bai hoc khong duoc de trong")
    @Size(min = 10, max = 200, message = "Tieu de phai tu 10 den 200 ky tu")
    private String tittle;

    @NotBlank(message = "Noi dung khong duoc de trong")
    private String content;

    @NotNull(message = "Vi tri bai hoc khong duoc de trong")
    @Positive(message = "Vi tri bai hoc phai la so duong va lon hon 0")
    private int position;
}
