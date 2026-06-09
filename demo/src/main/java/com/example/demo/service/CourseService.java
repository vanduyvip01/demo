package com.example.demo.service;

import com.example.demo.dto.requests.CourseCreateRequest;
import com.example.demo.dto.requests.CourseUpdateRequest;
import com.example.demo.dto.response.CourseResponse;
import com.example.demo.entity.Course;
import com.example.demo.enumtype.CourseStatus;
import com.example.demo.repository.CourseRepository;
import com.example.demo.service.interfaceService.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService implements ICourseService {

    private final CourseRepository courseRepository;

    @Override
    @Transactional
    public CourseResponse createCourse(CourseCreateRequest request) {
        Course course = Course.builder()
                .tittle(request.getTittle())
                .description(request.getDescription())
                .price(request.getPrice())
                .status(CourseStatus.DRAFT) // Mặc định là DRAFT khi tạo mới
                .build();

        Course savedCourse = courseRepository.save(course);
        return mapToResponse(savedCourse);
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khóa học với id: " + id));
        return mapToResponse(course);
    }

    @Override
    @Transactional
    public CourseResponse updateCourse(Long id, CourseUpdateRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khóa học với id: " + id));

        course.setTittle(request.getTittle());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());
        course.setStatus(request.getStatus());

        Course updatedCourse = courseRepository.save(course);
        return mapToResponse(updatedCourse);
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy khóa học với id: " + id);
        }
        courseRepository.deleteById(id);
    }

    // Hàm helper để convert sang DTO trả về
    private CourseResponse mapToResponse(Course course) {
        CourseResponse response = new CourseResponse();
        response.setId(course.getId());
        response.setTittle(course.getTittle());
        response.setDescription(course.getDescription());
        response.setStatus(course.getStatus());
        response.setPrice(course.getPrice());
        response.setCreatedAt(course.getCreatedAt());
        response.setUpdatedAt(course.getUpdatedAt());
        return response;
    }
}
