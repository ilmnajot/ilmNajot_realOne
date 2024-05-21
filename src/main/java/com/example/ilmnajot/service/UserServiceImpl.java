package com.example.ilmnajot.service;

import com.example.ilmnajot.entity.User;
import com.example.ilmnajot.enums.RoleName;
import com.example.ilmnajot.exception.UserException;
import com.example.ilmnajot.model.common.ApiResponse;
import com.example.ilmnajot.model.request.UserRequest;
import com.example.ilmnajot.model.response.LoginResponse;
import com.example.ilmnajot.model.response.UserResponse;
import com.example.ilmnajot.repository.UserRepository;
import com.example.ilmnajot.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    private final JwtProvider jwtProvider;

//
//    @Override
//    public LoginResponse addUser(UserRequest request) {
//        Optional<User> userByEmail = userRepository.findByUsername(request.getUsername());
//        if (userByEmail.isPresent()) {
//            throw new UserException("User is already exist", HttpStatus.CONFLICT);
//        }
//        if (!checkPassword(request)) {
//            throw new UserException("Password does not match, please try again", HttpStatus.CONFLICT);
//        }
//        User user = new User();
//        user.setFullName(request.getFullName());
//        user.setUsername(request.getUsername());
//        user.setPhoneNumber(request.getPhoneNumber());
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//        user.setRoleName(RoleName.ADMIN);
//        user.setEnabled(true);
//        userRepository.save(user);
//        String token = jwtProvider.generateToken(user);
//        LoginResponse loginResponse = new LoginResponse();
//        loginResponse.setToken(token);
////        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
//        return loginResponse;
//    }

    private boolean checkPassword(UserRequest request) {
        String pass = request.getPassword();
        String rePassword = request.getRePassword();
        return pass.equals(rePassword);
    }

    @Override
    public ApiResponse getUserById(Long userId) {
        User user = getUser(userId);
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        return new ApiResponse("User Found", true, userResponse);
    }

    @Override
    public ApiResponse getUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserException("No users found", HttpStatus.NOT_FOUND);
        }
        List<UserResponse> responseList = users
                .stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .toList();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("User response", responseList);
        responseMap.put("current page", 1);
        responseMap.put("totalItems", responseList.size());
        return new ApiResponse("Users Found", true, responseMap);
    }

    @Override
    public ApiResponse updateUser(Long userId, UserRequest request) {
        User user = getUser(userId);
        user.setId(userId);
        user.setFullName(request.getFullName());
        user.setUsername(request.getUsername());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPassword(request.getPassword());
//        user.setRoleName(request.getRoleName());
        User savedUser = userRepository.save(user);
        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
        return new ApiResponse("User Updated", true, userResponse);
    }

    @Override
    public ApiResponse deleteUser(Long userId) {
        User user = getUser(userId);
        if (user != null) {
            userRepository.deleteById(userId);
            return new ApiResponse("user deleted", true, "User deleted successfully");
        }
        throw new UserException("User not found", HttpStatus.NOT_FOUND);
    }

    @Override
    public ApiResponse getUserByName(String fullName) {
        Optional<User> userByName = userRepository.findByFullName(fullName);
        if (userByName.isPresent()) {
            User user = userByName.get();
            UserResponse userResponse = modelMapper.map(user, UserResponse.class);
            return new ApiResponse("USER FOUND", true, userResponse);
        }
        throw new UserException("User not found", HttpStatus.NOT_FOUND);
    }

    @Override
    public ApiResponse getUserByEmail(String email) {
        Optional<User> userByEmail = userRepository.findByUsername(email);
        if (userByEmail.isPresent()) {
            User user = userByEmail.get();
            UserResponse userResponse = modelMapper.map(user, UserResponse.class);
            return new ApiResponse("USER FOUND", true, userResponse);
        }
        throw new UserException("User not found", HttpStatus.NOT_FOUND);
    }

    private User getUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UserException("User not found", HttpStatus.NOT_FOUND);
    }

}
