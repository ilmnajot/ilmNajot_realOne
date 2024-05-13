package com.example.ilmnajot.repository;

import com.example.ilmnajot.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {


    @Query(value = "select * from Student where Student .email like %?1%", nativeQuery = true)
    Optional<Student> findUserByEmail(String email);

    Optional<Student> findByName(String name);
}
