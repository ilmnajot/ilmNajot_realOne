package com.example.ilmnajot.service;

import com.example.ilmnajot.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class UserDetailsServiceCustom implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceCustom(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findUserByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }
}
