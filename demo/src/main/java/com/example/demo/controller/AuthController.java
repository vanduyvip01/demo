package com.example.demo.controller;

import com.example.demo.dto.reponse.BaseResponse;
import com.example.demo.utils.JwtUtils;
import com.example.demo.utils.ResponseUtils;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")

public class AuthController {
    @GetMapping("/test-login")
    public String login(HttpSession session) {session.setAttribute(
                "user",
                "admin");
        return "LOGIN SUCCESS";
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(

            @RequestParam String username,
            @RequestParam String password
    ) {

        System.out.println(username);
        System.out.println(password);

        // CHECK ACCOUNT
        if (
                username.equals("admin") && password.equals("123")
        ) {

            String token = JwtUtils.generateToken(username);
            return ResponseEntity.ok(ResponseUtils.success(
                            200,
                            "Login successfully",
                            token
                    )
            );
        }
        // LOGIN FAIL
        return ResponseEntity.status(401).body(ResponseUtils.success(
                                401,
                                "Invalid username or password",
                                null));
    }

    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<Object>>
    logout(HttpSession session) {
        session.invalidate();

        return ResponseEntity.ok(ResponseUtils.success(
                        200,
                        "Logout success",
                        null));
    }
}