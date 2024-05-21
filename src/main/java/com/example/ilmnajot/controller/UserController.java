package com.example.ilmnajot.controller;

import com.example.ilmnajot.model.common.ApiResponse;
import com.example.ilmnajot.model.request.UserRequest;
import com.example.ilmnajot.model.response.LoginResponse;
import com.example.ilmnajot.service.UserService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping("/addUser")
//    public HttpEntity<?> addUser(@RequestBody UserRequest request) {
//        LoginResponse loginResponse = userService.addUser(request);
//        return loginResponse != null
//                ? ResponseEntity.status(HttpStatus.ACCEPTED).body(loginResponse)
//                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//    }

    @GetMapping("/getUser/{userId}")
    public HttpEntity<ApiResponse> getUser(@PathVariable Long userId) {
        ApiResponse apiResponse = userService.getUserById(userId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/getUsers")
    public HttpEntity<ApiResponse> getUsers() {
        ApiResponse apiResponse = userService.getUsers();
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PutMapping("/updateUser/{userId}")
    public HttpEntity<ApiResponse> updateUser(
            @PathVariable Long userId,
            @RequestBody UserRequest request) {
        ApiResponse apiResponse = userService.updateUser(userId, request);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping("/deleteUser/{userId}")
    public HttpEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        ApiResponse apiResponse = userService.deleteUser(userId);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping("/getUserByName")
    public HttpEntity<ApiResponse> getUserByName(@RequestParam(name = "name") String name) {
        ApiResponse apiResponse = userService.getUserByName(name);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.FOUND).body(apiResponse)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/getUserByEmail")
    public HttpEntity<ApiResponse> getUserByEmail(@RequestParam(name = "email") String email) {
        ApiResponse apiResponse = userService.getUserByEmail(email);
        return apiResponse != null
                ? ResponseEntity.status(HttpStatus.FOUND).body(apiResponse)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
