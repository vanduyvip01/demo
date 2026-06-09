package com.example.demo.controller;

import com.example.demo.dto.requests.LessonCreateRequest;
import com.example.demo.dto.requests.LessonUpdateRequest;
import com.example.demo.dto.response.LessonResponse;
import com.example.demo.service.LessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;
    @PostMapping("/{courseId}/lessons")
    public ResponseEntity<LessonResponse> createLesson(
            @PathVariable Long courseId,
            @Valid @RequestBody LessonCreateRequest request) {
        return new ResponseEntity<>(lessonService.createLesson(courseId, request), HttpStatus.CREATED);
    }
    @GetMapping("/{courseId}/lessons")
    public ResponseEntity<Page<LessonResponse>> getLessonsByCourse(
            @PathVariable Long courseId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(lessonService.getLessonsWithPaging(courseId, page, size));
    }
    @PutMapping("/{lessonId}")
    public ResponseEntity<LessonResponse> updateLesson(
            @PathVariable Long lessonId,
            @Valid @RequestBody LessonUpdateRequest request) {
        return ResponseEntity.ok(lessonService.updateLesson(lessonId, request));
    }
    @DeleteMapping("/{lessonId}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long lessonId) {
        lessonService.deleteLesson(lessonId);
        return ResponseEntity.noContent().build();
    }
}
