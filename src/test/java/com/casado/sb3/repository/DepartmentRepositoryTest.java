package com.casado.sb3.repository;

import com.casado.sb3.entity.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DepartmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DepartmentRepository departmentRepository;

    private Department department1;
    private Department department2;

    @BeforeEach
    void setUp() {
        // Create test data for Department entities
        department1 = new Department();
        department1.setName("Computer Science");
        department1.setDepartmentCode("CS_101");
        department1.setDescription("Department of Computer Science");
        department1.setBuilding("Building A");
        department1.setPhoneNumber("1234567890");

        department2 = new Department();
        department2.setName("Mathematics");
        department2.setDepartmentCode("MATH_102");
        department2.setDescription("Department of Mathematics");
        department2.setBuilding("Building B");
        department2.setPhoneNumber("0987654321");

        // Persist the departments
        entityManager.persist(department1);
        entityManager.persist(department2);
        entityManager.flush();
    }

    @Test
    @DisplayName("findDepartmentByNameIgnoreCase should return department when name exists (case insensitive)")
    void findDepartmentByNameIgnoreCase_WhenNameExists() {
        // Act
        Optional<Department> foundDepartment = departmentRepository.findDepartmentByNameIgnoreCase("Mathematics");

        // Assert
        assertTrue(foundDepartment.isPresent());
        assertEquals("Mathematics", foundDepartment.get().getName());
        assertEquals("MATH_102", foundDepartment.get().getDepartmentCode());
    }

    @Test
    @DisplayName("findDepartmentByNameIgnoreCase should return empty when name does not exist")
    void findDepartmentByNameIgnoreCase_WhenNameDoesNotExist() {
        // Act
        Optional<Department> foundDepartment = departmentRepository.findDepartmentByNameIgnoreCase("Biology");

        // Assert
        assertFalse(foundDepartment.isPresent());
    }

    @Test
    @DisplayName("findById should return department when id exists")
    void findById_WhenIdExists() {
        // Act
        Optional<Department> foundDepartment = departmentRepository.findById(1L);

        // Assert
        assertTrue(foundDepartment.isPresent());
        assertEquals("Computer Science", foundDepartment.get().getName());
        assertEquals("CS_101", foundDepartment.get().getDepartmentCode());
    }

    @Test
    @DisplayName("findById should return empty when id does not exist")
    void findById_WhenIdDoesNotExist() {
        // Act
        Optional<Department> foundDepartment = departmentRepository.findById(999L);

        // Assert
        assertFalse(foundDepartment.isPresent());
    }
}
