package com.example.ilmnajot.controller;

import com.example.ilmnajot.entity.User;
import com.example.ilmnajot.model.common.ApiResponse;
import com.example.ilmnajot.model.request.LoginForm;
import com.example.ilmnajot.model.request.UserRequest;
import com.example.ilmnajot.model.response.LoginResponse;
import com.example.ilmnajot.service.AuthService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public HttpEntity<?> register(@RequestBody UserRequest form) {
        LoginResponse register = authService.register(form);
        return register != null
                ? ResponseEntity.ok(register)
                : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginForm form) {
        LoginResponse authenticate = authService.authenticate(form);
        return authenticate != null
                ? ResponseEntity.ok(authenticate)
                : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

}
