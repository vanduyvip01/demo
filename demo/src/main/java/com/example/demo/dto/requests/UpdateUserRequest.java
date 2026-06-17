package com.example.demo.dto.requests;

import com.example.demo.enumtype.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {

    private String name;

    private int age;

    private Gender gender;

    private String email;

    private String phone;
}