package com.example.demo.dto.requests;

import com.example.demo.enumtype.Gender;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Min(value = 1, message = "Age must be greater than 0")
    private int age;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    @Pattern(
            regexp = "^0[0-9]{9}$",
            message = "Phone number invalid"
    )
    private String phone;
}
