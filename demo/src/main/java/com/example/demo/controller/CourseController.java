package com.example.demo.controller;

import com.example.demo.dto.requests.CourseCreateRequest;
import com.example.demo.dto.requests.CourseUpdateRequest;
import com.example.demo.dto.response.CourseResponse;
import com.example.demo.service.CourseService;
import com.example.demo.utils.ResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseResponse> createCourse(@Valid @RequestBody CourseCreateRequest request) {
        CourseResponse response = courseService.createCourse(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseResponse> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseUpdateRequest request) {
        return ResponseEntity.ok(courseService.updateCourse(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/publish")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseResponse> publishCourse(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.publishCourse(id));
    }

    @PatchMapping("/{id}/unpublish")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseResponse> unpublishCourse(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.unpublishCourse(id));
    }

    @PostMapping("/{courseId}/enroll")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> enrollCourse(@PathVariable Long courseId, Authentication authentication) {
        String studentEmail = authentication.getName();
        courseService.enroll(courseId, studentEmail);
        return ResponseEntity.ok(ResponseUtils.success("Đăng ký khóa học thành công!"));
    }

    @GetMapping("/my-courses")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getMyEnrolledCourses(Authentication authentication) {
        String studentEmail = authentication.getName();
        return ResponseEntity.ok(ResponseUtils.success(courseService.getCoursesByStudent(studentEmail)));
    }

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }
}