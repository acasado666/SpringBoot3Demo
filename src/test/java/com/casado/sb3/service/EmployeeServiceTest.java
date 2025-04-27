package com.casado.sb3.service;

import com.casado.sb3.dto.DepartmentDto;
import com.casado.sb3.dto.EmployeeDto;
import com.casado.sb3.entity.Department;
import com.casado.sb3.entity.Employee;
import com.casado.sb3.exception.EmployeeAlreadyExistsException;
import com.casado.sb3.exception.ResourceNotFoundException;
import com.casado.sb3.mapper.EmployeeMapper;
import com.casado.sb3.repository.DepartmentRepository;
import com.casado.sb3.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee1;
    private Employee employee2;
    private EmployeeDto employeeDto1;
    private EmployeeDto employeeDto2;
    private Department department;
    private DepartmentDto departmentDto;

    @BeforeEach
    void setUp() {
        // Create test department
        department = new Department();
        department.setId(1L);
        department.setName("Engineering");
        department.setDepartmentCode("ENG_101");
        department.setDescription("Engineering Department");
        department.setBuilding("Building A");
        department.setPhoneNumber("9234567890");
        department.setCreatedBy("Admin");
        department.setCreatedAt(LocalDateTime.now());

        // Create test data for Employee entities
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

        // Create test data for EmployeeDto objects
        departmentDto = new DepartmentDto();
        departmentDto.setName("Engineering");
        departmentDto.setDepartmentCode("ENG_101");
        departmentDto.setDescription("Engineering Department");
        departmentDto.setBuilding("Building A");
        departmentDto.setPhoneNumber("9234567890");
        departmentDto.setCreatedBy("Admin");
        departmentDto.setCreatedAt(LocalDateTime.now());

        employeeDto1 = new EmployeeDto();
        employeeDto1.setName("Mark");
        employeeDto1.setLastName("Gomez");
        employeeDto1.setAddress("123 Main St");
        employeeDto1.setEmail("mark@example.com");
        employeeDto1.setPhoneNumber("2234567890");
        employeeDto1.setDepartmentId(1L);
        employeeDto1.setDepartmentName("Engineering");

        employeeDto2 = new EmployeeDto();
        employeeDto2.setName("Maria");
        employeeDto2.setLastName("Garcia");
        employeeDto2.setAddress("456 Oak Ave");
        employeeDto2.setEmail("maria@example.com");
        employeeDto2.setPhoneNumber("0987654321");
        employeeDto2.setDepartmentId(1L);
        employeeDto2.setDepartmentName("Engineering");
    }

    @Test
    @DisplayName("getAllEmployees should return all employees")
    void getAllEmployees() {
        // Arrange
        List<Employee> employees = Arrays.asList(employee1, employee2);
        List<EmployeeDto> expectedDtos = Arrays.asList(employeeDto1, employeeDto2);
        
        when(employeeRepository.findAll()).thenReturn(employees);
        when(employeeMapper.toDtoList(employees)).thenReturn(expectedDtos);

        // Act
        List<EmployeeDto> result = employeeService.getAllEmployees();

        // Assert
        assertEquals(expectedDtos.size(), result.size());
        assertEquals(expectedDtos.get(0).getName(), result.get(0).getName());
        assertEquals(expectedDtos.get(1).getName(), result.get(1).getName());
        verify(employeeRepository, times(1)).findAll();
        verify(employeeMapper, times(1)).toDtoList(employees);
    }

    @Test
    @DisplayName("getEmployeeById should return employee when found")
    void getEmployeeById_Found() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee1));
        when(employeeMapper.toDto(employee1)).thenReturn(employeeDto1);

        // Act
        EmployeeDto result = employeeService.getEmployeeById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(employeeDto1.getName(), result.getName());
        assertEquals(employeeDto1.getLastName(), result.getLastName());
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeMapper, times(1)).toDto(employee1);
    }

    @Test
    @DisplayName("getEmployeeById should throw ResourceNotFoundException when not found")
    void getEmployeeById_NotFound() {
        // Arrange
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeById(999L));
        verify(employeeRepository, times(1)).findById(999L);
        verify(employeeMapper, never()).toDto(any(Employee.class));
    }

    @Test
    @DisplayName("getEmployeeByNameAndLastName should return employee when found")
    void getEmployeeByNameAndLastName_Found() {
        // Arrange
        String name = "Mark";
        String lastName = "Gomez";
        
        when(employeeRepository.findByNameAndLastNameIgnoreCase(name, lastName)).thenReturn(Optional.of(employee1));
        when(employeeMapper.toDto(employee1)).thenReturn(employeeDto1);

        // Act
        EmployeeDto result = employeeService.getEmployeeByNameAndLastName(name, lastName);

        // Assert
        assertNotNull(result);
        assertEquals(employeeDto1.getName(), result.getName());
        assertEquals(employeeDto1.getLastName(), result.getLastName());
        verify(employeeRepository, times(1)).findByNameAndLastNameIgnoreCase(name, lastName);
        verify(employeeMapper, times(1)).toDto(employee1);
    }

    @Test
    @DisplayName("getEmployeeByNameAndLastName should throw ResourceNotFoundException when not found")
    void getEmployeeByNameAndLastName_NotFound() {
        // Arrange
        String name = "Unknown";
        String lastName = "Person";
        
        when(employeeRepository.findByNameAndLastNameIgnoreCase(name, lastName)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeByNameAndLastName(name, lastName));
        verify(employeeRepository, times(1)).findByNameAndLastNameIgnoreCase(name, lastName);
        verify(employeeMapper, never()).toDto(any(Employee.class));
    }

    @Test
    @DisplayName("getEmployeeByPhone should return employee when found")
    void getEmployeeByPhone_Found() {
        // Arrange
        String phoneNumber = "1234567890";
        
        when(employeeRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.of(employee1));
        when(employeeMapper.toDto(employee1)).thenReturn(employeeDto1);

        // Act
        EmployeeDto result = employeeService.getEmployeeByPhone(phoneNumber);

        // Assert
        assertNotNull(result);
        assertEquals(employeeDto1.getName(), result.getName());
        assertEquals(employeeDto1.getPhoneNumber(), result.getPhoneNumber());
        verify(employeeRepository, times(1)).findByPhoneNumber(phoneNumber);
        verify(employeeMapper, times(1)).toDto(employee1);
    }

    @Test
    @DisplayName("getEmployeeByPhone should throw ResourceNotFoundException when not found")
    void getEmployeeByPhone_NotFound() {
        // Arrange
        String phoneNumber = "9999999999";
        
        when(employeeRepository.findByPhoneNumber(phoneNumber)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeByPhone(phoneNumber));
        verify(employeeRepository, times(1)).findByPhoneNumber(phoneNumber);
        verify(employeeMapper, never()).toDto(any(Employee.class));
    }

    @Test
    @DisplayName("createEmployee should create and return new employee")
    void createEmployee_Success() {
        // Arrange
        when(employeeRepository.findByPhoneNumber(employeeDto1.getPhoneNumber())).thenReturn(Optional.empty());
        when(departmentRepository.findDepartmentByNameIgnoreCase(anyString())).thenReturn(Optional.of(department));
        when(employeeMapper.toEntity(employeeDto1)).thenReturn(employee1);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee1);
        when(employeeMapper.toDto(employee1)).thenReturn(employeeDto1);

        // Act
        EmployeeDto result = employeeService.createEmployee(employeeDto1);

        // Assert
        assertNotNull(result);
        assertEquals(employeeDto1.getName(), result.getName());
        assertEquals(employeeDto1.getLastName(), result.getLastName());
        verify(employeeRepository, times(1)).findByPhoneNumber(employeeDto1.getPhoneNumber());
        verify(employeeMapper, times(1)).toEntity(employeeDto1);
        verify(employeeRepository, times(1)).save(any(Employee.class));
        verify(employeeMapper, times(1)).toDto(employee1);
    }

    @Test
    @DisplayName("createEmployee should throw EmployeeAlreadyExistsException when employee exists")
    void createEmployee_AlreadyExists() {
        // Arrange
        when(employeeRepository.findByPhoneNumber(employeeDto1.getPhoneNumber())).thenReturn(Optional.of(employee1));

        // Act & Assert
        assertThrows(EmployeeAlreadyExistsException.class, () -> employeeService.createEmployee(employeeDto1));

        verify(employeeRepository, times(1)).findByPhoneNumber(employeeDto1.getPhoneNumber());
        verify(employeeMapper, never()).toEntity(any(EmployeeDto.class));
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("updateEmployee should update and return true")
    void updateEmployee_Success() {
        // Arrange
        when(employeeRepository.findByPhoneNumber(employeeDto1.getPhoneNumber())).thenReturn(Optional.of(employee1));
        when(employeeMapper.toEntity(employeeDto1)).thenReturn(employee1);
        when(employeeRepository.save(employee1)).thenReturn(employee1);

        // Act
        boolean result = employeeService.updateEmployee(employeeDto1);

        // Assert
        assertTrue(result);
        verify(employeeRepository, times(1)).findByPhoneNumber(employeeDto1.getPhoneNumber());
        verify(employeeMapper, times(1)).toEntity(employeeDto1);
        verify(employeeRepository, times(1)).save(employee1);
    }

    @Test
    @DisplayName("updateEmployee should throw ResourceNotFoundException when employee not found")
    void updateEmployee_NotFound() {
        // Arrange
        when(employeeRepository.findByPhoneNumber(employeeDto1.getPhoneNumber())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> employeeService.updateEmployee(employeeDto1));
        verify(employeeRepository, times(1)).findByPhoneNumber(employeeDto1.getPhoneNumber());
        verify(employeeMapper, never()).toEntity(any(EmployeeDto.class));
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("deleteEmployee should delete and return true")
    void deleteEmployee_Success() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee1));
        doNothing().when(employeeRepository).deleteById(1L);

        // Act
        boolean result = employeeService.deleteEmployee(1L);

        // Assert
        assertTrue(result);
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deleteEmployee should throw ResourceNotFoundException when employee not found")
    void deleteEmployee_NotFound() {
        // Arrange
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> employeeService.deleteEmployee(999L));
        verify(employeeRepository, times(1)).findById(999L);
        verify(employeeRepository, never()).deleteById(anyLong());
    }
}