package com.example.demo.entity;

import com.example.demo.enumtype.Gender;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter

@Table(name = "users")

public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int age;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private String password;
    // LAZY LOAD
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="user_roles",
            joinColumns= @JoinColumn(name="user_id"),
            inverseJoinColumns= @JoinColumn(name="role_id")
    )
    private Set<Role> roles = new HashSet<>();
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.roles == null || this.roles.isEmpty()) {
            return Collections.emptyList();
        }
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }


    @Override
    public String getUsername() {
        return this.email;
    }

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Course> enrolledCourses = new ArrayList<>();

//    @Override
//    public boolean isAccountNonExpired() { return true; }
//
//    @Override
//    public boolean isAccountNonLocked() { return true; }
//
//    @Override
//    public boolean isCredentialsNonExpired() { return true; }
//
//    @Override
//    public boolean isEnabled() { return true; }
}

