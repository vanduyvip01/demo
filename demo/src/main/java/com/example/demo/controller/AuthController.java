package com.example.demo.controller;

import com.example.demo.dto.reponse.BaseResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AuthService;
import com.example.demo.utils.JwtUtils;
import com.example.demo.utils.ResponseUtils;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/test-login")
    public String login(HttpSession session) {
        session.setAttribute("user", "admin");
        return "LOGIN SUCCESS";
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<String>> login(@RequestBody LoginDto loginDto) {

        System.out.println("Email đăng nhập: " + loginDto.getEmail());

        User user = userRepository.findByEmail(loginDto.getEmail()).orElse(null);

        if (user != null && passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            String token = JwtUtils.generateToken(user.getEmail());
            return ResponseEntity.ok(ResponseUtils.success(token));
        }
        return ResponseEntity.badRequest().body(
                ResponseUtils.error(
                        40001,
                        "LOGIN_FAILED",
                        "Invalid email or password"
                )
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<Object>> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(ResponseUtils.success(null));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody User user) {
        return ResponseEntity.ok(authService.register(user));
    }
}

@Getter
@Setter
class LoginDto {
    private String email;
    private String password;
}