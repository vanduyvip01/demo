package com.example.demo.controller;

import com.example.demo.dto.reponse.BaseResponse;
import com.example.demo.dto.requests.CreateUserRequest;
import com.example.demo.dto.requests.UpdateUserRequest;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.ResponseUtils;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    // GET ALL USERS

    @GetMapping
    public ResponseEntity<BaseResponse<List<User>>> getUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(
                ResponseUtils.success(users));
    }

    // GET USER DETAIL

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<User>> getUserDetail(
            @PathVariable Long id) {

        User user = userService.getUserById(id);

        return ResponseEntity.ok(
                ResponseUtils.success(user));
    }

    // CREATE USER

    @PostMapping
    public ResponseEntity<BaseResponse<Void>>
    createUser(
            @Valid
            @RequestBody
            CreateUserRequest request
    ) {
        userService.createUser(request);

        return ResponseEntity.ok(ResponseUtils.success(null
                )
        );
    }

    // UPDATE USER

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>>
    updateUser(
            @PathVariable Long id,
            @Valid
            @RequestBody
            UpdateUserRequest request

    ) {

        userService.updateUser(id, request);
        return ResponseEntity.ok(ResponseUtils.success(null));
    }

    // DELETE USER

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>>
    deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.ok(ResponseUtils.success(null));
    }

    // STATS

    @GetMapping("/stats")
    public ResponseEntity<BaseResponse<Map<String,Object>>>
    getStats() {

        Map<String,Object> stats = userService.getUserStats();
        return ResponseEntity.ok(ResponseUtils.success(stats));
    }
}