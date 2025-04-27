package com.casado.sb3.init;

import com.casado.sb3.entity.Department;
import com.casado.sb3.entity.Employee;
import com.casado.sb3.repository.DepartmentRepository;
import com.casado.sb3.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) throws RuntimeException {

        // Clear existing (optional for dev testing)
        employeeRepository.deleteAll();
        departmentRepository.deleteAll();

        // Create departments
        Department engineering = new Department();
        engineering.setName("Engineering");

        Department hr = new Department();
        hr.setName("Human Resources");

        departmentRepository.saveAll(List.of(engineering, hr));

        // Create employees
        Employee antonio = new Employee();
        antonio.setName("Antonio Casadò");
        antonio.setDepartment(engineering);

        Employee alice = new Employee();
        alice.setName("Alice Smith");
        alice.setDepartment(engineering);

        Employee bob = new Employee();
        bob.setName("Bob Johnson");
        bob.setDepartment(engineering);

        Employee clara = new Employee();
        clara.setName("Clara White");
        clara.setDepartment(hr);

        Employee maria = new Employee();
        clara.setName("Maria Costa");
        clara.setDepartment(hr);

        employeeRepository.saveAll(List.of(antonio, alice, bob, clara, maria));

        System.out.println("Seed data inserted!");
    }
}
