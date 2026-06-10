package com.example.demo.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EnrollmentRequest {
    @NotNull(message = "userID khong duoc de trong")
    private long userId;

    @NotNull(message = "course ID khong duoc de trong")
    private long courseId;
}
