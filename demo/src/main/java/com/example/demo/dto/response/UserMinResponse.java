package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserMinResponse {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private String phone;
    private String gender;
    private Set<String> roles;
}
