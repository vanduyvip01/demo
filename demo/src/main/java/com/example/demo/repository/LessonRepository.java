package com.example.demo.repository;

import com.example.demo.entity.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LessonRepository extends JpaRepository<Lesson,Long> {
    Page<Lesson> findByCourseId(Long courseId, Pageable pageable);
}

