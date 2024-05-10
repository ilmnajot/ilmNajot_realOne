package com.example.ilmnajot.service;

import com.example.ilmnajot.entity.User;
import com.example.ilmnajot.exception.UserException;
import com.example.ilmnajot.model.ApiResponse;
import com.example.ilmnajot.model.request.UserRequest;
import com.example.ilmnajot.model.response.UserResponse;
import com.example.ilmnajot.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public ApiResponse addUser(UserRequest request) {
        Optional<User> userByEmail = userRepository.findUserByEmail(request.getEmail());
        if (userByEmail.isPresent()) {
            throw new UserException("User is already exist", HttpStatus.CONFLICT);
        }
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setAge(request.getAge());
        User savedUser = userRepository.save(user);
        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
        return new ApiResponse("User Added", true, userResponse);
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

        Map<String , Object> responseMap = new HashMap<>();
        responseMap.put("User response", responseList);
        responseMap.put("current page",1);
        responseMap.put("totalItems", responseList.size());
        return new ApiResponse("Users Found", true, responseMap);
    }

    @Override
    public ApiResponse updateUser(Long userId, UserRequest request) {
        User user = getUser(userId);
        user.setId(userId);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setAge(request.getAge());
        User savedUser = userRepository.save(user);
        UserResponse userResponse = modelMapper.map(savedUser, UserResponse.class);
        return new ApiResponse("User Updated", true, userResponse);
    }

    @Override
    public ApiResponse deleteUser(Long userId) {
        User user = getUser(userId);
        if (user != null){
        userRepository.deleteById(userId);
        return new ApiResponse("user deleted", true, "User deleted successfully");
        }
        throw new UserException("User not found", HttpStatus.NOT_FOUND);
    }

    @Override
    public ApiResponse getUserByName(String username) {
        Optional<User> userByName = userRepository.findUserByName(username);
        if (userByName.isPresent()) {
            User user = userByName.get();
            UserResponse userResponse = modelMapper.map(user, UserResponse.class);
            return new ApiResponse("USER FOUND", true, userResponse);
        }
        throw new UserException("User not found", HttpStatus.NOT_FOUND);
    }

    @Override
    public ApiResponse getUserByEmail(String email) {
        Optional<User> userByEmail = userRepository.findUserByEmail(email);
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
