package com.example.ilmnajot.service;

import com.example.ilmnajot.model.request.LoginForm;
import com.example.ilmnajot.model.request.UserRequest;
import com.example.ilmnajot.model.response.LoginResponse;


public interface AuthService {

    LoginResponse register(UserRequest form);

//    LoginResponse addUser(UserRequest request);

    LoginResponse authenticate(LoginForm form);
}
