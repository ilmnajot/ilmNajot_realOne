package com.example.ilmnajot.service;

import com.example.ilmnajot.entity.User;
import com.example.ilmnajot.exception.UserException;
import com.example.ilmnajot.model.common.ApiResponse;
import com.example.ilmnajot.model.request.LoginForm;
import com.example.ilmnajot.model.request.UserRequest;
import com.example.ilmnajot.model.response.LoginResponse;
import com.example.ilmnajot.repository.UserRepository;
import com.example.ilmnajot.security.jwt.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }


    @Override
    public ApiResponse register(UserRequest form) {
        Optional<User> userByEmail = userRepository.findUserByEmail(form.getEmail());
        if (userByEmail.isPresent()){
            throw new UserException("user with email " + form.getEmail() + " already exists", HttpStatus.CONFLICT);
        }
        User user = new User();
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setEmail(form.getEmail());
        user.setPassword(form.getPassword());
        user.setPassword(form.getPassword());
       throw new UserException("user with email: " + form.getEmail() +  "has been registered already", HttpStatus.CONFLICT);
    }

    @Override
    public ApiResponse login(LoginForm form) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                form.getEmail(),
                form.getPassword()
        ));
        String token = jwtProvider.generateToken(form.getEmail());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        return new ApiResponse("successfully login to the system", true, loginResponse);

    }
}
