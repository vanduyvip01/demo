package com.example.demo.service;

import com.example.demo.dto.requests.LessonCreateRequest;
import com.example.demo.dto.requests.LessonUpdateRequest;
import com.example.demo.dto.response.LessonResponse;
import com.example.demo.dto.response.PageResponse;
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

    @Transactional
    public LessonResponse createLesson(Long courseId, LessonCreateRequest request) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Khong tim thay khoa hoc"));
        Lesson lesson = Lesson.builder()
                .tittle(request.getTittle())
                .content(request.getContent())
                .position(request.getPosition())
                .course(course)
                .build();
        return mapToResponse(lessonRepository.save(lesson));
    }

    public PageResponse<LessonResponse> getLessonsWithPaging(Long courseId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("position").ascending());
        Page<Lesson> lessonPage = lessonRepository.findByCourseId(courseId, pageable);

        List<LessonResponse> dtoList = lessonPage.getContent().stream().map(this::mapToResponse).toList();
        return PageResponse.<LessonResponse>builder()
                .content(dtoList)
                .pageNumber(lessonPage.getNumber())
                .pageSize(lessonPage.getSize())
                .totalElements(lessonPage.getTotalElements())
                .totalPages(lessonPage.getTotalPages())
                .isLast(lessonPage.isLast())
                .build();
    }

    @Transactional
    public LessonResponse updateLesson(Long lessonId, LessonUpdateRequest request) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new NotFoundException("Khong tim thay bai hoc"));
        lesson.setTittle(request.getTittle());
        lesson.setContent(request.getContent());
        lesson.setPosition(request.getPosition());
        return mapToResponse(lessonRepository.save(lesson));
    }

    @Transactional
    public void deleteLesson(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new NotFoundException("Khong tim thay bai hoc"));
        lessonRepository.delete(lesson);
    }

    private LessonResponse mapToResponse(Lesson lesson) {
        LessonResponse response = new LessonResponse();
        response.setId(lesson.getId());
        response.setCourseId(lesson.getCourse().getId());
        response.setTittle(lesson.getTittle());
        response.setContent(lesson.getContent());
        response.setPosition(lesson.getPosition());
        return response;
    }
}