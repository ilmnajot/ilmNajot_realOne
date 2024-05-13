package com.example.ilmnajot.entity;

import com.example.ilmnajot.base.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Student extends BaseEntity {

    private String name;

    private String surname;

    private String email;

    private String age;

    private String gender;

    private String grade;

    private String school;
}
