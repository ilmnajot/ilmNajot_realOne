package com.example.ilmnajot.controller;

import com.example.ilmnajot.model.common.ApiResponse;
import com.example.ilmnajot.model.request.LoginForm;
import com.example.ilmnajot.model.request.UserRequest;
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

    @PostMapping("/signUp")
    public HttpEntity<ApiResponse> register(@RequestBody UserRequest form){
        ApiResponse apiResponse = authService.register(form);
        return apiResponse !=null
                ? ResponseEntity.ok(apiResponse)
                : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PostMapping("/singIn")
    public HttpEntity<ApiResponse> singIn(@RequestBody LoginForm form){
        ApiResponse apiResponse = authService.login(form);
        return apiResponse !=null
                ? ResponseEntity.ok(apiResponse)
                : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

}
