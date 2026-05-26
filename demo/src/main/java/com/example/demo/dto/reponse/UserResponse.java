package com.example.demo.dto.reponse;

import com.example.demo.enumtype.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse {

    private Long id;

    private String name;

    private int age;

    private Gender gender;

    private String email;

    private String phone;
}