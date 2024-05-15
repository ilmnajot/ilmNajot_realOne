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
    private final UserServiceImpl userService;

    public AuthServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtProvider jwtProvider, UserServiceImpl userService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }


    @Override
    public ApiResponse register(UserRequest form) {
        Optional<User> userByEmail = userRepository.findByEmail(form.getEmail());
        if (userByEmail.isPresent()) {
            throw new UserException("user with email " + form.getEmail() + " already exists", HttpStatus.CONFLICT);
        }
        ApiResponse apiResponse = userService.addUser(form);
        return new ApiResponse("registered", true, apiResponse);

    }
//        if (!checkPassword(form)){
//            throw new UserException("Password does not match, please try again", HttpStatus.CONFLICT);
//        }
//        User user = new User();
//        user.setFullName(form.getFullName());
//        user.setUsername(form.getUsername());
//        user.setEmail(form.getEmail());
//        user.setPhoneNumber(form.getPhoneNumber());
//        user.setPassword(form.getPassword());
//        user.setEnabled(true);
//       throw new UserException("user with email: " + form.getEmail() +  "has been registered already", HttpStatus.CONFLICT);
//    }
//    private boolean checkPassword(UserRequest request){
//        String pass = request.getPassword();
//        String rePassword = request.getRePassword();
//        return pass.equals(rePassword);
//    }


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
