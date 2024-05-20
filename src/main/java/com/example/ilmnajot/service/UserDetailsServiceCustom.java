package com.example.ilmnajot.service;

import com.example.ilmnajot.exception.UserException;
import com.example.ilmnajot.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceCustom implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceCustom(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username)  {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException("user not found"));
    }
}
