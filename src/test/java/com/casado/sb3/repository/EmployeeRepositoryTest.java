package com.casado.sb3.repository;

import com.casado.sb3.entity.Department;
import com.casado.sb3.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    private Department department;
    private Employee employee1;
    private Employee employee2;

    @BeforeEach
    void setUp() {
        // Create test data for Employee entities
        department = new Department();
        department.setId(1L);
        department.setName("Engineering");
        department.setDepartmentCode("ENG_101");
        department.setDescription("Engineering Department");
        department.setBuilding("Building A");
        department.setPhoneNumber("9234567890");
        department.setCreatedBy("Admin");
        department.setCreatedAt(LocalDateTime.now());

        employee1 = new Employee();
        employee1.setId(1L);
        employee1.setName("Mark");
        employee1.setLastName("Gomez");
        employee1.setAddress("123 Main St");
        employee1.setEmail("mark@example.com");
        employee1.setPhoneNumber("2234567890");
        employee1.setDepartment(department);

        employee2 = new Employee();
        employee2.setId(2L);
        employee2.setName("Maria");
        employee2.setLastName("Garcia");
        employee2.setAddress("456 Oak Ave");
        employee2.setEmail("maria@example.com");
        employee2.setPhoneNumber("0987654321");
        employee2.setDepartment(department);

        // Persist the departments
//        employeeRepository.save(employee1);
//        employeeRepository.save(employee2);
//        entityManager.persist(employee1);
//        entityManager.persist(employee2);
//        entityManager.flush();
    }

    @Test
    @DisplayName("Should save and find employee by id")
    void findById_WhenIdExists() {
        // Act
        Optional<Employee> found = employeeRepository.findById(1L);

        // Assert
        assertTrue(found.isPresent());
        assertEquals("Mark", found.get().getName());
        assertEquals("Gomez", found.get().getLastName());
    }

    @Test
    @DisplayName("findById should return empty when id does not exist")
    void findById_WhenIdDoesNotExist() {
        // Act
        Optional<Employee> found = employeeRepository.findById(999L);

        // Assert
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("findByPhoneNumber should find employee by phone number")
    void findByPhoneNumber_WhenPhoneNumberExists() {

        // Act
        Optional<Employee> found = employeeRepository.findByPhoneNumber("0987654321");

        // Assert
        assertTrue(found.isPresent());
        assertEquals("0987654321", found.get().getPhoneNumber());
    }

    @Test
    @DisplayName("findByPhoneNumber should return empty")
    void findByPhoneNumber_WhenPhoneNumberDoesNotExists() {

        // Act
        Optional<Employee> found = employeeRepository.findByPhoneNumber("6666666666");

        // Assert
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("findByNameAndLastNameIgnoreCase should find employee by name and last name ignoring case")
    void findByNameAndLastNameIgnoreCase_WhenNameExists() {
        // Act
        Optional<Employee> found = employeeRepository.findByNameAndLastNameIgnoreCase("maria", "garcia");

        // Assert
        assertTrue(found.isPresent());
        assertEquals("Maria", found.get().getName());
        assertEquals("Garcia", found.get().getLastName());
    }

    @Test
    @DisplayName("findByNameAndLastNameIgnoreCase should return empty when name does not exist")
    void findByNameAndLastNameIgnoreCase_WhenNameDoesNotExists() {

        // Act
        Optional<Employee> found = employeeRepository.findByNameAndLastNameIgnoreCase("bob", "marley");

        // Assert
        assertFalse(found.isPresent());
    }
}
