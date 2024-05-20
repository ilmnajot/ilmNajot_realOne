package com.example.ilmnajot.model.request;

import com.example.ilmnajot.enums.RoleName;
import lombok.Data;

@Data
public class UserRequest {

    private String fullName;

    private String username;

    private String phoneNumber;

    private String password;

    private String rePassword;

//    private RoleName roleName;

}


