package com.example.demo.entity;

import com.example.demo.enumtype.Gender;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter

@Table(name = "users")

public class User {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String name;

    private int age;

    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)

    private Gender gender;


    // LAZY LOAD
    @ManyToMany(fetch = FetchType.LAZY)

    @JoinTable(

            name="user_roles",

            joinColumns=

            @JoinColumn(name="user_id"),

            inverseJoinColumns=
            @JoinColumn(name="role_id")
    )
//    Set
    private Set<Role> roles = new HashSet<>();
}
