package com.school.service;

import com.school.entity.Student;
import org.springframework.data.domain.Page;

/**
 * 学生服务接口
 */
public interface StudentService {

    /**
     * 获取学生列表（分页）
     */
    Page<Student> getStudents(int page, int size, String grade, String status, String keyword);

    /**
     * 获取学生详情
     */
    Student getStudentById(String id);

    /**
     * 创建学生
     */
    Student createStudent(Student student);

    /**
     * 更新学生信息
     */
    Student updateStudent(String id, Student student);

    /**
     * 删除学生
     */
    void deleteStudent(String id);

    /**
     * 根据学号获取学生
     */
    Student getStudentByStudentNo(String studentNo);

    /**
     * 获取学生总数
     */
    long getTotalCount();

    /**
     * 获取指定状态的学生数
     */
    long getCountByStatus(String status);
}
