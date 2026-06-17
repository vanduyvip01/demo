package com.example.demo.dto.response;

import com.example.demo.enumtype.Gender;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private int age;
    private Gender gender;
    private String email;
    private String phone;
    private String password;
    private Set<String> roles;
}