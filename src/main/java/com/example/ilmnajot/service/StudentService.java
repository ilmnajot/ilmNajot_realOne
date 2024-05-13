package com.example.ilmnajot.service;

import com.example.ilmnajot.model.common.ApiResponse;
import com.example.ilmnajot.model.request.StudentRequest;

public interface StudentService {

    ApiResponse addStudent(StudentRequest request);

    ApiResponse getStudentById(Long userId);

    ApiResponse getStudents();

    ApiResponse updateStudent(Long userId, StudentRequest request);

    ApiResponse deleteStudent(Long userId);

    ApiResponse getUserByName(String name);

    ApiResponse getUserByEmail(String email);
}
