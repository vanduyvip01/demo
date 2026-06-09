package com.example.demo.service;

import com.example.demo.dto.requests.LessonCreateRequest;
import com.example.demo.dto.requests.LessonUpdateRequest;
import com.example.demo.dto.response.LessonResponse;
import com.example.demo.entity.Course;
import com.example.demo.entity.Lesson;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.LessonRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    // 1. Logic Tạo mới
    @Transactional
    public LessonResponse createLesson(Long courseId, LessonCreateRequest request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found"));

        Lesson lesson = Lesson.builder()
                .tittle(request.getTittle())
                .content(request.getContent())
                .position(request.getPosition())
                .course(course)
                .build();

        return mapToResponse(lessonRepository.save(lesson));
    }

    // 2. Logic Lấy danh sách kèm Paging (Sắp xếp theo vị trí bài học tăng dần)
    public Page<LessonResponse> getLessonsWithPaging(Long courseId, int page, int size) {
        // Tạo Pageable sắp xếp theo cột 'position' tăng dần (Ascending)
        Pageable pageable = PageRequest.of(page, size, Sort.by("position").ascending());

        Page<Lesson> lessonPage = lessonRepository.findByCourseId(courseId, pageable);

        // Map luồng Page từ Entity sang DTO response
        return lessonPage.map(this::mapToResponse);
    }

    // 3. Logic Cập nhật
    @Transactional
    public LessonResponse updateLesson(Long lessonId, LessonUpdateRequest request) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new NotFoundException("Lesson not found"));

        lesson.setTittle(request.getTittle());
        lesson.setContent(request.getContent());
        lesson.setPosition(request.getPosition());

        return mapToResponse(lessonRepository.save(lesson));
    }

    // 4. Logic Xóa
    @Transactional
    public void deleteLesson(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new NotFoundException("Lesson not found"));
        lessonRepository.delete(lesson);
    }

    // Helper map sang Response DTO
    private LessonResponse mapToResponse(Lesson lesson) {
        LessonResponse response = new LessonResponse();
        response.setId(lesson.getId());
        response.setCourseId(lesson.getCourse().getId());
        response.setTittle(lesson.getTittle()); // Đảm bảo chữ title đúng chính tả nhé bồ!
        response.setContent(lesson.getContent());
        response.setPosition(lesson.getPosition());
        return response;
    }
}