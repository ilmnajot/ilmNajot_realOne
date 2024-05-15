package com.example.ilmnajot.service;

import com.example.ilmnajot.entity.Student;
import com.example.ilmnajot.exception.UserException;
import com.example.ilmnajot.model.common.ApiResponse;
import com.example.ilmnajot.model.request.StudentRequest;
import com.example.ilmnajot.model.response.StudentResponse;
import com.example.ilmnajot.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {


    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;


    @Override
    public ApiResponse addStudent(StudentRequest request) {
        Optional<Student> userByEmail = studentRepository.findUserByEmail(request.getEmail());
        if (userByEmail.isPresent()) {
            throw new UserException("Student with email " + request.getEmail() + " already exists", HttpStatus.CONFLICT);
        }
        Student student = new Student();
        student.setName(request.getName());
        student.setSurname(request.getSurname());
        student.setEmail(request.getEmail());
        student.setAge(request.getAge());
        student.setGender(request.getGender());
        student.setGrade(request.getGrade());
        student.setSchool(request.getSchool());
        Student savedSchool = studentRepository.save(student);
        StudentResponse studentResponse = modelMapper.map(savedSchool, StudentResponse.class);

        return new ApiResponse("student saved", true, studentResponse);
    }

    @Override
    public ApiResponse getStudentById(Long userId) {
        Student student = getStudent(userId);
        StudentResponse studentResponse = modelMapper.map(student, StudentResponse.class);
        return new ApiResponse("student with id:", true, studentResponse);
    }

    @Override
    public ApiResponse getStudents() {
        List<Student> students = studentRepository.findAll();
        if (students.isEmpty()) {
            throw new UserException("Students not found", HttpStatus.NOT_FOUND);
        }
        List<StudentResponse> studentResponses =
                students
                        .stream()
                        .map(student -> modelMapper.map(student, StudentResponse.class))
                        .toList();
        Map<String, Object> response = new HashMap<>();
        response.put("students", studentResponses);
        response.put("current page", 1);
        response.put("size", studentResponses.size());
        return new ApiResponse("students found", true, response);
    }

    @Override
    public ApiResponse updateStudent(Long userId, StudentRequest request) {
        Student student = getStudent(userId);
        student.setId(userId);
        student.setName(request.getName());
        student.setSurname(request.getSurname());
        student.setEmail(request.getEmail());
        student.setAge(request.getAge());
        student.setGender(request.getGender());
        student.setGrade(request.getGrade());
        student.setSchool(request.getSchool());
        Student save = studentRepository.save(student);
        StudentResponse studentResponse = modelMapper.map(save, StudentResponse.class);

        return new ApiResponse("th student updated", true, studentResponse);

    }

    @Override
    public ApiResponse deleteStudent(Long userId) {
        Student student = getStudent(userId);
        if (student != null) {
            studentRepository.deleteById(userId);
            return new ApiResponse("student is deleted successfully", true, "well done");
        }
        throw new UserException("student not found", HttpStatus.NOT_FOUND);
    }

    @Override
    public ApiResponse getUserByName(String name) {
        Optional<Student> userByName = studentRepository.findByName(name);
        if (userByName.isPresent()) {
            Student student = userByName.get();
            StudentResponse userResponse = modelMapper.map(true, StudentResponse.class);
            return new ApiResponse("USER FOUND", true, userResponse);
        }
        throw new UserException("User not found", HttpStatus.NOT_FOUND);
    }

    @Override
    public ApiResponse getUserByEmail(String email) {
        Optional<Student> userByEmail = studentRepository.findUserByEmail(email);
        if (userByEmail.isPresent()) {
            Student student = userByEmail.get();
            StudentResponse userResponse = modelMapper.map(student, StudentResponse.class);
            return new ApiResponse("USER FOUND", true, userResponse);
        }
        return null;
    }

    private Student getStudent(Long studentId) {
        Optional<Student> optionalUser = studentRepository.findById(studentId);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UserException("User not found", HttpStatus.NOT_FOUND);
    }
}
