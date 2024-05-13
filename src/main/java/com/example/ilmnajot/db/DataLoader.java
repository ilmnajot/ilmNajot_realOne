package com.example.ilmnajot.db;

import com.example.ilmnajot.entity.User;
import com.example.ilmnajot.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
 @Value("${spring.sql.init.mode}")
    private String mode;
    private final UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        if (mode.equals("always")){
            userRepository.save(
                    User
                            .builder()
                            .firstName("Elbekjon")
                            .lastName("Umar")
                            .email("ilmnajot2021@gmail.com")
                            .password("password")
                            .age("29")
                            .build());
        }
    }
}
