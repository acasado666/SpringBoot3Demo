package com.casado.sb3.repository;

import com.casado.sb3.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findById(Long id);

    Optional<Employee> findByPhoneNumber(String phoneNumber);

    Optional<Employee> findByNameAndLastNameIgnoreCase(String name, String lastName);
}