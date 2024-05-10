package com.example.ilmnajot.entity;

import com.example.ilmnajot.base.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Setter
@Getter
public class User extends BaseEntity {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String age;



}
