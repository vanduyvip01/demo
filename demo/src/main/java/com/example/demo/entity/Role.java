package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Entity
@Getter
@Setter

@Table(name = "roles")

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;

    @Override
    public String toString(){
        return name;

    }

}