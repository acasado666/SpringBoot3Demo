package com.casado.sb3.repository;

import com.casado.sb3.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByNameContainingIgnoreCase(String name);

    List<Employee> findByDepartmentId(Long departmentId);

    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByNameAndLastNameIgnoreCase(String name, String lastName);

    Optional<Employee> findByPhoneNumber(String phoneNumber);
}