package com.edu.schoolsoapapi.soap.repository;

import com.edu.schoolsoapapi.soap.bean.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStudentRepository extends JpaRepository<Student,Long>{

}
