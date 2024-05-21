package com.example.ilmnajot.security.config;

import com.example.ilmnajot.repository.UserRepository;
import com.example.ilmnajot.service.UserDetailsServiceCustom;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

    private final UserRepository userRepository;
    private final UserDetailsServiceCustom userDetailsServiceCustom;
    private final PasswordEncoder passwordEncoder;

    public ApplicationConfig(UserRepository userRepository, UserDetailsServiceCustom userDetailsServiceCustom, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userDetailsServiceCustom = userDetailsServiceCustom;
        this.passwordEncoder = passwordEncoder;
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> userRepository
                .findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
}
