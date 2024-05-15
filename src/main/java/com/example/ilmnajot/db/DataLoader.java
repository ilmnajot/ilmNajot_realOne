package com.example.ilmnajot.db;

import com.example.ilmnajot.entity.Student;
import com.example.ilmnajot.entity.User;
import com.example.ilmnajot.repository.StudentRepository;
import com.example.ilmnajot.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
 @Value("${spring.sql.init.mode}")
    private String mode;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;



    @Override
    public void run(String... args) throws Exception {
        if (mode.equals("always")){
            userRepository.save(
                    User
                            .builder()
                            .fullName("Elbekjon")
                            .username("Elbek_Umar")
                            .email("ilmnajot2021@gmail.com")
                            .phoneNumber("+998994107354")
                            .password(passwordEncoder.encode("password"))
                            .build());
            studentRepository.save(
                    Student
                            .builder()
                            .name("Student name")
                            .surname("Student surname")
                            .email("student@gmail.com")
                            .age("20")
                            .gender("male")
                            .grade("7 green")
                            .school("SAMPS")
                            .build());

        }
    }
}
