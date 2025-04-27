package com.casado.sb3.controller;

import com.casado.sb3.constants.ProjectConstants;
import com.casado.sb3.dto.EmployeeDto;
import com.casado.sb3.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        // Create a validator that always returns valid
        Validator mockValidator = new Validator() {
            @Override
            public boolean supports(Class<?> clazz) {
                return true;
            }

            @Override
            public void validate(Object target, org.springframework.validation.Errors errors) {
                // Do nothing - always valid
            }
        };

        mockMvc = MockMvcBuilders
                .standaloneSetup(employeeController)
                .setValidator(mockValidator)
                .build();
    }

    @Test
    @DisplayName("GET /api/employees/all returns all employees")
    void getAllEmployees() throws Exception {
        // Create test data
        EmployeeDto employee1 = new EmployeeDto();
        employee1.setName("John");
        employee1.setLastName("Doe");
        employee1.setPhoneNumber("1234567890");
        employee1.setEmail("john.doe@example.com");
        employee1.setAddress("123 Main St");
        employee1.setDepartmentId(1L);
        employee1.setDepartmentName("Engineering");

        EmployeeDto employee2 = new EmployeeDto();
        employee2.setName("Jane");
        employee2.setLastName("Smith");
        employee2.setPhoneNumber("0987654321");
        employee2.setEmail("jane.smith@example.com");
        employee2.setAddress("456 Oak Ave");
        employee2.setDepartmentId(2L);
        employee2.setDepartmentName("Marketing");

        List<EmployeeDto> employees = Arrays.asList(employee1, employee2);

        // Mock service method
        Mockito.when(employeeService.getAllEmployees()).thenReturn(employees);

        // Perform request and verify response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("John")))
                .andExpect(jsonPath("$[0].lastName", is("Doe")))
                .andExpect(jsonPath("$[0].phoneNumber", is("1234567890")))
                .andExpect(jsonPath("$[0].departmentName", is("Engineering")))
                .andExpect(jsonPath("$[1].name", is("Jane")))
                .andExpect(jsonPath("$[1].lastName", is("Smith")))
                .andExpect(jsonPath("$[1].phoneNumber", is("0987654321")))
                .andExpect(jsonPath("$[1].departmentName", is("Marketing")));
    }

    @Test
    @DisplayName("GET /api/employees/fetchById returns employee by id")
    void getEmployeeById() throws Exception {
        // Create test data
        EmployeeDto dto = new EmployeeDto();
        dto.setName("John");
        dto.setLastName("Doe");
        dto.setPhoneNumber("1234567890");
        dto.setEmail("john.doe@example.com");
        dto.setAddress("123 Main St");
        dto.setDepartmentId(1L);
        dto.setDepartmentName("Engineering");

        // Mock service method
        Mockito.when(employeeService.getEmployeeById(1L)).thenReturn(dto);

        // Perform request and verify response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/fetchById").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.phoneNumber", is("1234567890")))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")))
                .andExpect(jsonPath("$.address", is("123 Main St")))
                .andExpect(jsonPath("$.departmentName", is("Engineering")));
    }

    @Test
    @DisplayName("GET /api/employees/fetchByName returns employee by name and last name")
    void getEmployeeByNameAndLastName() throws Exception {
        // Create test data
        EmployeeDto dto = new EmployeeDto();
        dto.setName("John");
        dto.setLastName("Doe");
        dto.setPhoneNumber("1234567890");
        dto.setEmail("john.doe@example.com");
        dto.setAddress("123 Main St");
        dto.setDepartmentId(1L);
        dto.setDepartmentName("Engineering");

        // Mock service method
        Mockito.when(employeeService.getEmployeeByNameAndLastName("John", "Doe")).thenReturn(dto);

        // Perform request and verify response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/fetchByName")
                        .param("name", "John")
                        .param("lastName", "Doe"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.phoneNumber", is("1234567890")))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")))
                .andExpect(jsonPath("$.address", is("123 Main St")))
                .andExpect(jsonPath("$.departmentName", is("Engineering")));
    }

    @Test
    @DisplayName("GET /api/employees/fetchByPhone returns employee by phone")
    void getEmployeeByPhone() throws Exception {
        // Create test data
        EmployeeDto dto = new EmployeeDto();
        dto.setName("John");
        dto.setLastName("Doe");
        dto.setPhoneNumber("1234567890");
        dto.setEmail("john.doe@example.com");
        dto.setAddress("123 Main St");
        dto.setDepartmentId(1L);
        dto.setDepartmentName("Engineering");

        // Mock service method
        Mockito.when(employeeService.getEmployeeByPhone("1234567890")).thenReturn(dto);

        // Perform request and verify response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/fetchByPhone")
                        .param("phone", "1234567890"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.phoneNumber", is("1234567890")))
                .andExpect(jsonPath("$.email", is("john.doe@example.com")))
                .andExpect(jsonPath("$.address", is("123 Main St")))
                .andExpect(jsonPath("$.departmentName", is("Engineering")));
    }

    @Test
    @DisplayName("POST /api/employees/createEmployee creates a new employee")
    void createEmployee() throws Exception {
        // Create test data
        EmployeeDto dto = new EmployeeDto();
        dto.setName("John");
        dto.setLastName("Doe");
        dto.setPhoneNumber("1234567890");
        dto.setEmail("john.doe@example.com");
        dto.setAddress("123 Main St");
        dto.setDepartmentId(1L);
        dto.setDepartmentName("Engineering");

        // Convert DTO to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String employeeJson = objectMapper.writeValueAsString(dto);

        // Mock service method
        Mockito.when(employeeService.createEmployee(any(EmployeeDto.class))).thenReturn(dto);

        // Perform request and verify response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/employees/createEmployee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode", is(ProjectConstants.STATUS_201)))
                .andExpect(jsonPath("$.statusMsg", is(ProjectConstants.MESSAGE_201)));
    }

    @Test
    @DisplayName("PUT /api/employees/updateEmployee updates an employee - success")
    void updateEmployeeSuccess() throws Exception {
        // Create test data
        EmployeeDto dto = new EmployeeDto();
        dto.setName("John");
        dto.setLastName("Doe");
        dto.setPhoneNumber("1234567890");
        dto.setEmail("john.doe@example.com");
        dto.setAddress("123 Main St");
        dto.setDepartmentId(1L);
        dto.setDepartmentName("Engineering");

        // Convert DTO to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String employeeJson = objectMapper.writeValueAsString(dto);

        // Mock service method
        Mockito.when(employeeService.updateEmployee(any(EmployeeDto.class))).thenReturn(true);

        // Perform request and verify response
        mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/updateEmployee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is(ProjectConstants.STATUS_200)))
                .andExpect(jsonPath("$.statusMsg", is(ProjectConstants.MESSAGE_200)));
    }

    @Test
    @DisplayName("PUT /api/employees/updateEmployee updates an employee - failure")
    void updateEmployeeFailure() throws Exception {
        // Create test data
        EmployeeDto dto = new EmployeeDto();
        dto.setName("John");
        dto.setLastName("Doe");
        dto.setPhoneNumber("1234567890");
        dto.setEmail("john.doe@example.com");
        dto.setAddress("123 Main St");
        dto.setDepartmentId(1L);
        dto.setDepartmentName("Engineering");

        // Convert DTO to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String employeeJson = objectMapper.writeValueAsString(dto);

        // Mock service method
        Mockito.when(employeeService.updateEmployee(any(EmployeeDto.class))).thenReturn(false);

        // Perform request and verify response
        mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/updateEmployee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson))
                .andExpect(status().isExpectationFailed())
                .andExpect(jsonPath("$.statusCode", is(ProjectConstants.STATUS_417)))
                .andExpect(jsonPath("$.statusMsg", is(ProjectConstants.MESSAGE_417_UPDATE)));
    }

    @Test
    @DisplayName("DELETE /api/employees/{id} deletes an employee - success")
    void deleteEmployeeSuccess() throws Exception {
        // Mock service method
        Mockito.when(employeeService.deleteEmployee(1L)).thenReturn(true);

        // Perform request and verify response
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is(ProjectConstants.STATUS_200)))
                .andExpect(jsonPath("$.statusMsg", is(ProjectConstants.MESSAGE_200)));
    }

    @Test
    @DisplayName("DELETE /api/employees/{id} deletes an employee - failure")
    void deleteEmployeeFailure() throws Exception {
        // Mock service method
        Mockito.when(employeeService.deleteEmployee(1L)).thenReturn(false);

        // Perform request and verify response
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/employees/1"))
                .andExpect(status().isExpectationFailed())
                .andExpect(jsonPath("$.statusCode", is(ProjectConstants.STATUS_417)))
                .andExpect(jsonPath("$.statusMsg", is(ProjectConstants.MESSAGE_417_DELETE)));
    }
}
