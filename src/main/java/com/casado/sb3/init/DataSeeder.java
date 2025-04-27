package com.casado.sb3.init;

import com.casado.sb3.entity.Department;
import com.casado.sb3.entity.Employee;
import com.casado.sb3.repository.DepartmentRepository;
import com.casado.sb3.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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
        engineering.setDepartmentCode("ALG_101");
        engineering.setDescription("Engineers are the ones who make the machines work.");
        engineering.setBuilding("Building 1, 1st floor");
        engineering.setCreatedBy("Admin");
        engineering.setPhoneNumber("1234567890");
        engineering.setCreatedAt(LocalDateTime.now());

        Department hr = new Department();
        hr.setName("Human Resources");
        hr.setDepartmentCode("HR_301");
        hr.setDescription("HR are the ones who take cares of employees issues.");
        hr.setBuilding("Building 2, 1st floor");
        hr.setCreatedBy("Admin");
        hr.setPhoneNumber("2345678901");
        hr.setCreatedAt(LocalDateTime.now());

        Department accounting = new Department();
        accounting.setName("Accounting");
        accounting.setDepartmentCode("ACC_321");
        accounting.setDescription("ACC are the ones who take cares of department billing issues.");
        accounting.setBuilding("Building 4, 1st floor");
        accounting.setCreatedBy("Admin");
        accounting.setPhoneNumber("3456789012");
        accounting.setCreatedAt(LocalDateTime.now());

        Department computer = new Department();
        computer.setName("Computer Science");
        computer.setDepartmentCode("CS_101");
        computer.setDescription("Software Developers are the ones that create applications.");
        computer.setBuilding("Building A");
        computer.setCreatedBy("Admin");
        computer.setPhoneNumber("4567890123");
        computer.setCreatedAt(LocalDateTime.now());

        departmentRepository.saveAll(List.of(engineering, hr, accounting, computer));

        // Create employees
        Employee antonio = new Employee();
        antonio.setName("Antonio");
        antonio.setLastName("Casadò");
        antonio.setAddress("123 Main St");
        antonio.setEmail("RiMw0@example.com");
        antonio.setPhoneNumber("123-456-7890");
        antonio.setCreatedBy("Admin");
        antonio.setCreatedAt(LocalDateTime.now());
        antonio.setDepartment(engineering);

        Employee alice = new Employee();
        alice.setName("Alice");
        alice.setLastName("Smith");
        alice.setAddress("234 Main St");
        alice.setEmail("sedeX@example.com");
        alice.setPhoneNumber("223-456-7890");
        alice.setCreatedBy("Admin");
        alice.setCreatedAt(LocalDateTime.now());
        alice.setDepartment(engineering);

        Employee bob = new Employee();
        bob.setName("Bob");
        bob.setLastName("Johnson");
        bob.setAddress("353 Main St");
        bob.setEmail("f9yjI@example.com");
        bob.setPhoneNumber("323-456-7890");
        bob.setCreatedBy("Admin");
        bob.setCreatedAt(LocalDateTime.now());
        bob.setDepartment(accounting);

        Employee clara = new Employee();
        clara.setName("Clara");
        clara.setLastName("White");
        clara.setAddress("478 Main St");
        clara.setPhoneNumber("423-456-7890");;
        clara.setEmail("qRZ0d@example.com");
        clara.setCreatedBy("Admin");
        clara.setCreatedAt(LocalDateTime.now());
        clara.setDepartment(hr);

        Employee maria = new Employee();
        maria.setName("Maria");
        maria.setLastName("Costa");
        maria.setEmail("V2I5I@example.com");
        maria.setAddress("512 Main St");
        maria.setPhoneNumber("523-456-7890");;
        maria.setCreatedBy("Admin");
        maria.setCreatedAt(LocalDateTime.now());
        maria.setDepartment(hr);

        Employee matthew = new Employee();
        matthew.setName("Matthew");
        matthew.setLastName("Webber");
        matthew.setEmail("MaWI5I@example.com");
        matthew.setAddress("663 Main St");
        matthew.setPhoneNumber("623-456-7890");;
        matthew.setCreatedBy("Admin");
        matthew.setCreatedAt(LocalDateTime.now());
        matthew.setDepartment(computer);

        employeeRepository.saveAll(List.of(antonio, alice, bob, clara, maria, matthew));

        System.out.println("Seed data inserted!");
    }
}
