package com.edu.schoolsoapapi.soap.repository;

import com.edu.schoolsoapapi.soap.bean.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICourseRepository extends JpaRepository<Course, Integer> {
}
