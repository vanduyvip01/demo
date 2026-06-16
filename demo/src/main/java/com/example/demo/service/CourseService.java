package com.example.demo.service;

import com.example.demo.dto.requests.CourseCreateRequest;
import com.example.demo.dto.requests.CourseUpdateRequest;
import com.example.demo.dto.response.CourseResponse;
import com.example.demo.entity.Course;
import com.example.demo.entity.User;
import com.example.demo.enumtype.CourseStatus;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.interfaceService.ICourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService implements ICourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CourseResponse createCourse(CourseCreateRequest request) {
        Course course = Course.builder()
                .tittle(request.getTittle())
                .description(request.getDescription())
                .price(request.getPrice())
                .status(CourseStatus.DRAFT)
                .build();

        Course savedCourse = courseRepository.save(course);
        return mapToResponse(savedCourse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> getAllCourses() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = false;
        if (authentication != null && authentication.isAuthenticated()) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            isAdmin = authorities.stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        }

        List<Course> courses;

        if (isAdmin) {
            courses = courseRepository.findAll();
        } else {
            courses = courseRepository.findByStatus(CourseStatus.PUBLISHED);
        }
        return courses.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Khong tim thay khoa hoc voi ID: " + id));

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && course.getStatus() != CourseStatus.PUBLISHED) {
            throw new RuntimeException("Khóa học này hiện chưa được mở công khai!");
        }

        return mapToResponse(course);
    }

    @Override
    @Transactional
    public CourseResponse updateCourse(Long id, CourseUpdateRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Khong tim thay khoa hoc voi ID: " + id));

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
            throw new RuntimeException("Khong tim thay khoa hoc voi ID: " + id);
        }
        courseRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CourseResponse publishCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Khong tim thay khoa hoc voi ID " + id));

        if (course.getStatus() == CourseStatus.PUBLISHED) {
            throw new RuntimeException("Khoa hoc nay da duoc xuat ban tu truoc roi");
        }
        course.setStatus(CourseStatus.PUBLISHED);
        Course updatedCourse = courseRepository.save(course);
        return mapToResponse(updatedCourse);
    }

    @Override
    @Transactional
    public CourseResponse unpublishCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Khong tim thay khoa hoc"));

        course.setStatus(CourseStatus.DRAFT);
        Course updatedCourse = courseRepository.save(course);
        return mapToResponse(updatedCourse);
    }

    @Override
    @Transactional
    public void enroll(Long courseId, String studentEmail) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khóa học với ID: " + courseId));
        User student = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin tài khoản người dùng"));
        course.getUsers().add(student);
        courseRepository.save(course);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> getCoursesByStudent(String studentEmail) {
        User student = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin tài khoản user"));

        return student.getEnrolledCourses().stream()
                .map(this::mapToResponse)
                .toList();
    }

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