package com.casado.sb3.service;

import com.casado.sb3.dto.DepartmentDto;
import com.casado.sb3.entity.Department;
import com.casado.sb3.exception.DepartmentAlreadyExistsException;
import com.casado.sb3.mapper.DepartmentMapper;
import com.casado.sb3.repository.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DepartmentMapper departmentMapper;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department1;
    private Department department2;
    private DepartmentDto departmentDto1;
    private DepartmentDto departmentDto2;

    @BeforeEach
    void setUp() {
        // Create test data for Department entities
        department1 = new Department();
        department1.setId(1L);
        department1.setName("Computer Science");
        department1.setDepartmentCode("CS_101");
        department1.setDescription("Department of Computer Science");
        department1.setBuilding("Building A");
        department1.setPhoneNumber("1234567890");

        department2 = new Department();
        department2.setId(2L);
        department2.setName("Accounting");
        department2.setDepartmentCode("ACC_321");
        department2.setDescription("Department of Accounting");
        department2.setBuilding("Building B");
        department2.setPhoneNumber("0987654321");

        // Create test data for DepartmentDto objects
        departmentDto1 = new DepartmentDto();
        departmentDto1.setName("Computer Science");
        departmentDto1.setDepartmentCode("CS_101");
        departmentDto1.setDescription("Department of Computer Science");
        departmentDto1.setBuilding("Building A");
        departmentDto1.setPhoneNumber("1234567890");

        departmentDto2 = new DepartmentDto();
        departmentDto2.setName("Accounting");
        departmentDto2.setDepartmentCode("ACC_321");
        departmentDto2.setDescription("Department of Accounting");
        departmentDto2.setBuilding("Building B");
        departmentDto2.setPhoneNumber("0987654321");
    }

    @Test
    @DisplayName("getAllDepartments should return all departments")
    void getAllDepartments() {
        // Arrange
        List<Department> departments = Arrays.asList(department1, department2);
        List<DepartmentDto> expectedDtos = Arrays.asList(departmentDto1, departmentDto2);

        when(departmentRepository.findAll()).thenReturn(departments);
        when(departmentMapper.toDto(department1)).thenReturn(departmentDto1);
        when(departmentMapper.toDto(department2)).thenReturn(departmentDto2);

        // Act
        List<DepartmentDto> result = departmentService.getAllDepartments();

        // Assert
        assertEquals(expectedDtos.size(), result.size());
        assertEquals(expectedDtos.get(0).getName(), result.get(0).getName());
        assertEquals(expectedDtos.get(1).getName(), result.get(1).getName());
        verify(departmentRepository, times(1)).findAll();
        verify(departmentMapper, times(2)).toDto(any(Department.class));
    }

    @Test
    @DisplayName("getDepartmentById should return department when found")
    void getDepartmentById_Found() {
        // Arrange
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department1));
        when(departmentMapper.toDto(department1)).thenReturn(departmentDto1);

        // Act
        DepartmentDto result = departmentService.getDepartmentById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(departmentDto1.getName(), result.getName());
        assertEquals(departmentDto1.getDepartmentCode(), result.getDepartmentCode());
        verify(departmentRepository, times(1)).findById(1L);
        verify(departmentMapper, times(1)).toDto(department1);
    }

    @Test
    @DisplayName("getDepartmentById should throw EntityNotFoundException when not found")
    void getDepartmentById_NotFound() {
        // Arrange
        when(departmentRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> departmentService.getDepartmentById(999L));
        verify(departmentRepository, times(1)).findById(999L);
        verify(departmentMapper, never()).toDto(any(Department.class));
    }

    @Test
    @DisplayName("createDepartment should create and return new department")
    void createDepartment_Success() {
        // Arrange
        when(departmentRepository.findDepartmentByNameIgnoreCase(departmentDto1.getName())).thenReturn(Optional.empty());
        when(departmentMapper.toEntity(departmentDto1)).thenReturn(department1);
        when(departmentRepository.save(any(Department.class))).thenReturn(department1);
        when(departmentMapper.toDto(department1)).thenReturn(departmentDto1);

        // Act
        DepartmentDto result = departmentService.createDepartment(departmentDto1);

        // Assert
        assertNotNull(result);
        assertEquals(departmentDto1.getName(), result.getName());
        assertEquals(departmentDto1.getDepartmentCode(), result.getDepartmentCode());
        verify(departmentRepository, times(1)).findDepartmentByNameIgnoreCase(departmentDto1.getName());
        verify(departmentMapper, times(1)).toEntity(departmentDto1);
        verify(departmentRepository, times(1)).save(any(Department.class));
        verify(departmentMapper, times(1)).toDto(department1);
    }

    @Test
    @DisplayName("createDepartment should throw DepartmentAlreadyExistsException when department exists")
    void createDepartment_AlreadyExists() {
        // Arrange
        when(departmentRepository.findDepartmentByNameIgnoreCase(departmentDto1.getName())).thenReturn(Optional.of(department1));

        // Act
        assertThrows(DepartmentAlreadyExistsException.class, () -> departmentService.createDepartment(departmentDto1));

        // Assert
        verify(departmentRepository, times(1)).findDepartmentByNameIgnoreCase(departmentDto1.getName());
        verify(departmentMapper, never()).toEntity(any(DepartmentDto.class));
        verify(departmentRepository, never()).save(any(Department.class));
    }

    @Test
    @DisplayName("updateDepartment should update and return true")
    void updateDepartment_Success() {
        // Arrange
        when(departmentRepository.findDepartmentByNameIgnoreCase(departmentDto1.getName())).thenReturn(Optional.of(department1));
        when(departmentMapper.toEntity(departmentDto1)).thenReturn(department1);
        when(departmentRepository.save(department1)).thenReturn(department1);

        // Act
        boolean result = departmentService.updateDepartment(departmentDto1);

        // Assert
        assertTrue(result);
        verify(departmentRepository, times(1)).findDepartmentByNameIgnoreCase(departmentDto1.getName());
        verify(departmentMapper, times(1)).toEntity(departmentDto1);
        verify(departmentRepository, times(1)).save(department1);
    }

    @Test
    @DisplayName("deleteDepartment should delete and return true")
    void deleteDepartment() {
        // Arrange
        doNothing().when(departmentRepository).deleteById(1L);

        // Act
        boolean result = departmentService.deleteDepartment(1L);

        // Assert
        assertTrue(result);
        verify(departmentRepository, times(1)).deleteById(1L);
    }
}