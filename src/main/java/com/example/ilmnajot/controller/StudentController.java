package com.example.ilmnajot.controller;

import com.example.ilmnajot.model.common.ApiResponse;
import com.example.ilmnajot.model.request.StudentRequest;
import com.example.ilmnajot.service.StudentService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/addStudent")
    public HttpEntity<ApiResponse> addStudent(@RequestBody StudentRequest request){
        ApiResponse apiResponse = studentService.addStudent(request);
        return apiResponse!=null
                ? ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    @GetMapping("/getStudent/{studentId}")
    public HttpEntity<ApiResponse> getStudent(@PathVariable Long studentId){
        ApiResponse apiResponse = studentService.getStudentById(studentId);
        return apiResponse!=null
                ? ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    @GetMapping("/getStudents")
    public HttpEntity<ApiResponse> getStudents(){
        ApiResponse apiResponse = studentService.getStudents();
        return apiResponse!=null
                ? ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    @PutMapping("/updateStudent/{studentId}")
    public HttpEntity<ApiResponse> updateStudent(
            @PathVariable Long studentId,
            @RequestBody StudentRequest request){
        ApiResponse apiResponse = studentService.updateStudent(studentId, request);
        return apiResponse!=null
                ? ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
    @DeleteMapping("/deleteStudent/{studentId}")
    public HttpEntity<ApiResponse> deleteStudent(@PathVariable Long studentId){
        ApiResponse apiResponse = studentService.deleteStudent(studentId);
        return apiResponse!=null
                ? ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    @GetMapping("/getStudentByName")
    public HttpEntity<ApiResponse> getStudentByName(@RequestParam(name = "name") String name){
        ApiResponse apiResponse = studentService.getUserByName(name);
        return apiResponse!=null
                ? ResponseEntity.status(HttpStatus.FOUND).body(apiResponse)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @GetMapping("/getStudentByEmail")
    public HttpEntity<ApiResponse> getStudentByEmail(@RequestParam(name = "email") String email){
        ApiResponse apiResponse = studentService.getUserByEmail(email);
        return apiResponse!=null
                ? ResponseEntity.status(HttpStatus.FOUND).body(apiResponse)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
