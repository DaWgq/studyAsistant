package com.school.service.impl;

import com.school.entity.Student;
import com.school.exception.BusinessException;
import com.school.repository.StudentRepository;
import com.school.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 学生服务实现
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Page<Student> getStudents(int page, int size, String grade, String status, String keyword) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return studentRepository.searchStudents(keyword, grade, status, pageable);
    }

    @Override
    public Student getStudentById(String id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new BusinessException(2001, "学生不存在"));
    }

    @Override
    @Transactional
    public Student createStudent(Student student) {
        if (studentRepository.existsByStudentNo(student.getStudentNo())) {
            throw new BusinessException(2002, "学号已存在");
        }
        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public Student updateStudent(String id, Student student) {
        Student existingStudent = getStudentById(id);
        
        // 更新字段
        if (student.getName() != null) {
            existingStudent.setName(student.getName());
        }
        if (student.getGender() != null) {
            existingStudent.setGender(student.getGender());
        }
        if (student.getGrade() != null) {
            existingStudent.setGrade(student.getGrade());
        }
        if (student.getMajor() != null) {
            existingStudent.setMajor(student.getMajor());
        }
        if (student.getPhone() != null) {
            existingStudent.setPhone(student.getPhone());
        }
        if (student.getAddress() != null) {
            existingStudent.setAddress(student.getAddress());
        }
        if (student.getStatus() != null) {
            existingStudent.setStatus(student.getStatus());
        }
        if (student.getEnrollmentYear() != null) {
            existingStudent.setEnrollmentYear(student.getEnrollmentYear());
        }
        if (student.getAvatarUrl() != null) {
            existingStudent.setAvatarUrl(student.getAvatarUrl());
        }

        return studentRepository.save(existingStudent);
    }

    @Override
    @Transactional
    public void deleteStudent(String id) {
        if (!studentRepository.existsById(id)) {
            throw new BusinessException(2001, "学生不存在");
        }
        studentRepository.deleteById(id);
    }

    @Override
    public Student getStudentByStudentNo(String studentNo) {
        return studentRepository.findByStudentNo(studentNo)
                .orElseThrow(() -> new BusinessException(2001, "学生不存在"));
    }

    @Override
    public long getTotalCount() {
        return studentRepository.count();
    }

    @Override
    public long getCountByStatus(String status) {
        return studentRepository.countByStatus(status);
    }
}
