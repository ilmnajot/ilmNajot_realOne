package com.example.ilmnajot.service;

import com.example.ilmnajot.model.common.ApiResponse;
import com.example.ilmnajot.model.request.UserRequest;
import com.example.ilmnajot.model.response.LoginResponse;

public interface UserService {

//    LoginResponse addUser(UserRequest request);

    ApiResponse getUserById(Long userId);

    ApiResponse getUsers();

    ApiResponse updateUser(Long userId, UserRequest request);

    ApiResponse deleteUser(Long userId);

    ApiResponse getUserByName(String username);

    ApiResponse getUserByEmail(String email);

//    LoginResponse addUser(UserRequest request);
}
