package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(User userRequest) {
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email nay da duoc dang ky roi");
        }

        String hashedPassword = passwordEncoder.encode(userRequest.getPassword());
        userRequest.setPassword(hashedPassword);

        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Khong tim thay quyen user trong he thong"));

        userRequest.setRoles(Collections.singleton(defaultRole));

        User savedUser = userRepository.save(userRequest);
        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setAge(savedUser.getAge());
        response.setEmail(savedUser.getEmail());
        response.setPhone(savedUser.getPhone());
        response.setGender(savedUser.getGender());
        response.setPassword(savedUser.getPassword());
        response.setRoles(Collections.singleton("ROLE_USER"));

        return response;
    }
}