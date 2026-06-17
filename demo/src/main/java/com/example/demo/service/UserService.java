package com.example.demo.service;

import com.example.demo.ErrorCodeEnum.ErrorCode;
import com.example.demo.dto.requests.CreateUserRequest;
import com.example.demo.dto.requests.UpdateUserRequest;
import com.example.demo.dto.response.UserMinResponse;
import com.example.demo.entity.User;
import com.example.demo.entity.Role; // 🛑 Nhớ đảm bảo có import Entity Role của bạn nhé
import com.example.demo.exception.BusinessException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.*;
import java.util.stream.Collectors;
import com.example.demo.dto.response.UserStatsResponse;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void testLazy(){
        User user = userRepository.findAll().getFirst();
        System.out.println(user.getName());
        System.out.println(user.getRoles());
    }
    // ARRAY List
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User createUser(CreateUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setAge(request.getAge());
        user.setGender(request.getGender());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Lỗi hệ thống: Chưa khởi tạo ROLE_USER dưới Database!"));

        Set<Role> defaultRoles = new HashSet<>();
        defaultRoles.add(userRole);
        user.setRoles(defaultRoles);

        return userRepository.save(user);
    }

    public UserMinResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        UserMinResponse dto = new UserMinResponse();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setAge(user.getAge());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setGender(user.getGender() != null ? user.getGender().toString() : null);

        if (user.getRoles() != null) {
            Set<String> roleNames = user.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toSet());
            dto.setRoles(roleNames);
        }

        return dto;
    }

    @Transactional
    public void updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));

        user.setName(request.getName());
        user.setAge(request.getAge());
        user.setGender(request.getGender());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        userRepository.save(user);
    }

    public Page<User> getUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        userRepository.delete(user);
    }

    // STREAM API
    public Map<String, Object> getUserStats() {
        List<User> users = userRepository.findAll();
        Map<String, Object> result = new HashMap<>();

        result.put("totalUsers", users.size());
        result.put("userNames", users.stream().map(User::getName).toList());

        List<UserMinResponse> minifiedUsers = users.stream().map(user -> {
            UserMinResponse dto = new UserMinResponse();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setAge(user.getAge());
            dto.setEmail(user.getEmail());
            dto.setPhone(user.getPhone());
            dto.setGender(user.getGender() != null ? user.getGender().toString() : null);

            if (user.getRoles() != null) {
                Set<String> roleNames = user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet());
                dto.setRoles(roleNames);
            }
            return dto;
        }).toList();

        List<UserMinResponse> adultUsers = minifiedUsers.stream()
                .filter(u -> users.stream().anyMatch(original -> original.getId().equals(u.getId()) && original.getAge() >= 18))
                .toList();
        result.put("adultUsers", adultUsers);

        long userWithGenderCount = users.stream().filter(u -> u.getGender() != null).count();
        result.put("userWithGenderCount", userWithGenderCount);

        List<UserStatsResponse> group = users.stream()
                .filter(u -> u.getGender() != null)
                .collect(Collectors.groupingBy(u -> u.getGender().toString(), Collectors.counting()))
                .entrySet().stream()
                .map(e -> new UserStatsResponse(e.getKey(), e.getValue()))
                .toList();
        result.put("groupByGender", group);

        result.put("users", minifiedUsers);

        return result;
    }
}