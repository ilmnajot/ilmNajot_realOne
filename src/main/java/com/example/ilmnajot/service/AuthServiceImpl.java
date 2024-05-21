package com.example.ilmnajot.service;

import com.example.ilmnajot.entity.User;
import com.example.ilmnajot.enums.RoleName;
import com.example.ilmnajot.exception.UserException;
import com.example.ilmnajot.model.request.LoginForm;
import com.example.ilmnajot.model.request.UserRequest;
import com.example.ilmnajot.model.response.LoginResponse;
import com.example.ilmnajot.repository.UserRepository;
import com.example.ilmnajot.security.jwt.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final UserServiceImpl userService;

    private final JwtProvider jwtProvider;

    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, UserServiceImpl userService, JwtProvider jwtProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public LoginResponse register(UserRequest request) {
        Optional<User> userByEmail = userRepository.findByUsername(request.getUsername());
        if (userByEmail.isPresent()) {
            throw new UserException("User is already exist", HttpStatus.CONFLICT);
        }
        if (!checkPassword(request)) {
            throw new UserException("Password does not match, please try again", HttpStatus.CONFLICT);
        }
        User user = new User();
        user.setFullName(request.getFullName());
        user.setUsername(request.getUsername());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoleName(RoleName.ADMIN);
        user.setEnabled(true);
        userRepository.save(user);
        String token = jwtProvider.generateToken(user);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
//        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
        return loginResponse;
    }

//    @Override
//    public LoginResponse addUser(UserRequest request) {
//        return null;
//    }

    private boolean checkPassword(UserRequest request) {
        String pass = request.getPassword();
        String rePassword = request.getRePassword();
        return pass.equals(rePassword);
    }


    @Override
    public LoginResponse authenticate(LoginForm form) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                form.getUsername(),
                form.getPassword()
        ));
        var user = userRepository.findByUsername(form.getUsername()).orElseThrow();
        String token = jwtProvider.generateToken(user);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        return loginResponse;
    }
//        String token = jwtProvider.generateToken(form.getUsername());
//        LoginResponse loginResponse = new LoginResponse();
//        loginResponse.setToken(token);
//        return new ApiResponse("successfully login to the system", true, loginResponse);

}
