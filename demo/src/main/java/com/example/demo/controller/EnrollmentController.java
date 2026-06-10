package com.example.demo.controller;

import com.example.demo.dto.requests.EnrollmentRequest;
import com.example.demo.dto.response.EnrollmentResponse;
import com.example.demo.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<EnrollmentResponse> enrollCourse(@Valid @RequestBody EnrollmentRequest request) {
        return new ResponseEntity<>(enrollmentService.enrollCourse(request), HttpStatus.CREATED);
    }
}
