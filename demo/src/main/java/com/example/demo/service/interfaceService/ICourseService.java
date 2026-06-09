package com.example.demo.service.interfaceService;

import com.example.demo.dto.requests.CourseCreateRequest;
import com.example.demo.dto.requests.CourseUpdateRequest;
import com.example.demo.dto.response.CourseResponse;

import java.util.List;

public interface ICourseService {
    CourseResponse createCourse(CourseCreateRequest request);
    List<CourseResponse> getAllCourses();
    CourseResponse getCourseById(Long id);
    CourseResponse updateCourse(Long id, CourseUpdateRequest request);
    void deleteCourse(Long id);
}
