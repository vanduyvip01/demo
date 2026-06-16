package com.example.demo.dto.requests;


import com.example.demo.enumtype.Gender;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import lombok.Data;

@Data
public class CreateUserRequest {

    @NotBlank(message = "NAME_REQUIRED")
    private String name;

    @Min(value = 1, message = "AGE_INVALID")
    private int age;

    @NotNull(message = "GENDER_REQUIRED")
    private Gender gender;

    @Email(message = "EMAIL_INVALID")
    @NotBlank(message = "EMAIL_REQUIRED")
    private String email;
    @Column(nullable = false)
    private String password;
    @Pattern(
            regexp = "^0[0-9]{9}$",
            message = "PHONE_INVALID")
    private String phone;
}

