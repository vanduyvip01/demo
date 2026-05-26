package com.example.demo.controller;

import com.example.demo.dto.reponse.BaseResponse;
import com.example.demo.dto.requests.CreateUserRequest;
import com.example.demo.dto.requests.UpdateUserRequest;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.ResponseUtils;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController


@RequestMapping("/users")

public class UserController {

    private final UserService userService;
    @GetMapping("/lazy")

    public String test(){
        userService.testLazy();
        return "OK";
    }

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET API
    // 200 + response body json
    @GetMapping("/{id}")
    public User get(
            @PathVariable
            Long id
    ) {
        return userService.getUserById(id);
    }

    // viết request tạo
//    trả ra void và 204
//    tìm hiểu response entity *200 201 *204 *400 403 *404 *500 503
    // POST API

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam
            String username,
            @RequestParam
            String password,
            HttpSession session
    ) {

        if (username.equals("admin") && password.equals("123")) {
            session.setAttribute("user", username);
            return ResponseEntity.ok("Login success");
        }

        return ResponseEntity
                .status(401)
                .body("Login failed");
    }
    @PostMapping

    public ResponseEntity<BaseResponse<Void>> createUser(@Valid @RequestBody CreateUserRequest request){
        userService.createUser(request);
        return ResponseEntity.ok(ResponseUtils.success(
                        200,
                        "Add user success",
                        null));
    }
    // get detail 1 user
    @GetMapping("/detail/{id}")
    public ResponseEntity<BaseResponse<User>> getUserDetail(@PathVariable Long id) {

        User user = userService.getUserById(id);
        return ResponseEntity.ok(ResponseUtils.success(
                        200,
                        "Get user detail success",
                        user));
    }
    // update 1 user
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> updateUser(
            @PathVariable Long id,
            @RequestBody
            UpdateUserRequest request
    ) {
        userService.updateUser(id, request);
        return ResponseEntity.ok(ResponseUtils.success(
                        200,
                        "Update user success",
                        null
                )
        );
    }
    // thêm 30 item vào bảng user và get list user theo page
    @GetMapping
    public ResponseEntity<BaseResponse<List<User>>>
    getUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(ResponseUtils.success(
                        200,
                        "Get users success",
                        users));
    }
    // delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteUser(
            @PathVariable Long id
    ) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ResponseUtils.success(
                        200,
                        "Delete user success",
                        null));
    }
//    với update, create và delete, thêm Transactional, tìm hiểu về các cơ chế transaction
@GetMapping("/stats")
    public ResponseEntity<Map<String,Object>> stats(){
        List<User> users = userService.getAllUsers();
        Map<String,Object> result = new HashMap<>();
        result.put("totalUsers", users.size());
        result.put("users", users);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/stream")
    public ResponseEntity<Map<String,Object>>
    stream(){

        return ResponseEntity.ok(

                userService.getUserStats()

        );
    }
}