package com.casado.sb3.controller;

import com.casado.sb3.constants.ProjectConstants;
import com.casado.sb3.dto.DepartmentDto;
import com.casado.sb3.service.DepartmentService;
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
class DepartmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

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
                .standaloneSetup(departmentController)
                .setValidator(mockValidator)
                .build();
    }

    @Test
    @DisplayName("GET /api/departments/all returns all departments")
    void getAllDepartments() throws Exception {
        // Create test data
        DepartmentDto department1 = new DepartmentDto();
        department1.setName("Computer Science");
        department1.setDepartmentCode("CS_101");
        department1.setDescription("Department of Computer Science");
        department1.setBuilding("Building A");
        department1.setPhoneNumber("1234567890");

        DepartmentDto department2 = new DepartmentDto();
        department2.setName("Mathematics");
        department2.setDepartmentCode("MATH_102");
        department2.setDescription("Department of Mathematics");
        department2.setBuilding("Building B");
        department2.setPhoneNumber("0987654321");

        List<DepartmentDto> departments = Arrays.asList(department1, department2);

        // Mock service method
        Mockito.when(departmentService.getAllDepartments()).thenReturn(departments);

        // Perform request and verify response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/departments/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Computer Science")))
                .andExpect(jsonPath("$[0].departmentCode", is("CS_101")))
                .andExpect(jsonPath("$[0].description", is("Department of Computer Science")))
                .andExpect(jsonPath("$[0].building", is("Building A")))
                .andExpect(jsonPath("$[0].phoneNumber", is("1234567890")))
                .andExpect(jsonPath("$[1].name", is("Mathematics")))
                .andExpect(jsonPath("$[1].departmentCode", is("MATH_102")))
                .andExpect(jsonPath("$[1].description", is("Department of Mathematics")))
                .andExpect(jsonPath("$[1].building", is("Building B")))
                .andExpect(jsonPath("$[1].phoneNumber", is("0987654321")));
    }

    @Test
    @DisplayName("GET /api/departments/{id} returns department by id")
    void getDepartmentById() throws Exception {
        // Create test data
        DepartmentDto department = new DepartmentDto();
        department.setName("Computer Science");
        department.setDepartmentCode("CS_101");
        department.setDescription("Department of Computer Science");
        department.setBuilding("Building A");
        department.setPhoneNumber("1234567890");

        // Mock service method
        Mockito.when(departmentService.getDepartmentById(1L)).thenReturn(department);

        // Perform request and verify response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/departments/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Computer Science")))
                .andExpect(jsonPath("$.departmentCode", is("CS_101")))
                .andExpect(jsonPath("$.description", is("Department of Computer Science")))
                .andExpect(jsonPath("$.building", is("Building A")))
                .andExpect(jsonPath("$.phoneNumber", is("1234567890")));
    }

    @Test
    @DisplayName("POST /api/departments creates a new department")
    void createDepartment() throws Exception {
        // Create test data
        DepartmentDto department = new DepartmentDto();
        department.setName("Computer Science");
        department.setDepartmentCode("CS_101");
        department.setDescription("Department of Computer Science");
        department.setBuilding("Building A");
        department.setPhoneNumber("1234567890");

        // Convert DTO to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String departmentJson = objectMapper.writeValueAsString(department);

        // Mock service method
        Mockito.when(departmentService.createDepartment(any(DepartmentDto.class))).thenReturn(department);

        // Perform request and verify response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(departmentJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode", is(ProjectConstants.STATUS_201)))
                .andExpect(jsonPath("$.statusMsg", is(ProjectConstants.MESSAGE_201)));
    }

    @Test
    @DisplayName("PUT /api/departments updates a department - success")
    void updateDepartmentSuccess() throws Exception {
        // Create test data
        DepartmentDto department = new DepartmentDto();
        department.setName("Computer Science");
        department.setDepartmentCode("CS_101");
        department.setDescription("Department of Computer Science");
        department.setBuilding("Building A");
        department.setPhoneNumber("1234567890");

        // Convert DTO to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String departmentJson = objectMapper.writeValueAsString(department);

        // Mock service method
        Mockito.when(departmentService.updateDepartment(any(DepartmentDto.class))).thenReturn(true);

        // Perform request and verify response
        mockMvc.perform(MockMvcRequestBuilders.put("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(departmentJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is(ProjectConstants.STATUS_200)))
                .andExpect(jsonPath("$.statusMsg", is(ProjectConstants.MESSAGE_200)));
    }

    @Test
    @DisplayName("PUT /api/departments updates a department - failure")
    void updateDepartmentFailure() throws Exception {
        // Create test data
        DepartmentDto department = new DepartmentDto();
        department.setName("Computer Science");
        department.setDepartmentCode("CS_101");
        department.setDescription("Department of Computer Science");
        department.setBuilding("Building A");
        department.setPhoneNumber("1234567890");

        // Convert DTO to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String departmentJson = objectMapper.writeValueAsString(department);

        // Mock service method
        Mockito.when(departmentService.updateDepartment(any(DepartmentDto.class))).thenReturn(false);

        // Perform request and verify response
        mockMvc.perform(MockMvcRequestBuilders.put("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(departmentJson))
                .andExpect(status().isExpectationFailed())
                .andExpect(jsonPath("$.statusCode", is(ProjectConstants.STATUS_417)))
                .andExpect(jsonPath("$.statusMsg", is(ProjectConstants.MESSAGE_417_UPDATE)));
    }

    @Test
    @DisplayName("DELETE /api/departments/{id} deletes a department - success")
    void deleteDepartmentSuccess() throws Exception {
        // Mock service method
        Mockito.when(departmentService.deleteDepartment(1L)).thenReturn(true);

        // Perform request and verify response
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/departments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is(ProjectConstants.STATUS_200)))
                .andExpect(jsonPath("$.statusMsg", is(ProjectConstants.MESSAGE_200)));
    }

    @Test
    @DisplayName("DELETE /api/departments/{id} deletes a department - failure")
    void deleteDepartmentFailure() throws Exception {
        // Mock service method
        Mockito.when(departmentService.deleteDepartment(1L)).thenReturn(false);

        // Perform request and verify response
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/departments/1"))
                .andExpect(status().isExpectationFailed())
                .andExpect(jsonPath("$.statusCode", is(ProjectConstants.STATUS_417)))
                .andExpect(jsonPath("$.statusMsg", is(ProjectConstants.MESSAGE_417_DELETE)));
    }
}