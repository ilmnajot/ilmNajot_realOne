package com.example.ilmnajot.model.response;

import com.example.ilmnajot.enums.RoleName;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;

    private String fullName;

    private String username;

    private String phoneNumber;

    private RoleName roleName;
//
//    private String password;
//
//    private String rePassword;
}
