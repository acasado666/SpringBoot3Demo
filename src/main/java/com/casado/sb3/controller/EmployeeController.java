package com.casado.sb3.controller;

import com.casado.sb3.constants.ProjectConstants;
import com.casado.sb3.dto.EmployeeDto;
import com.casado.sb3.dto.ErrorResponseDto;
import com.casado.sb3.dto.ResponseDto;
import com.casado.sb3.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "CRUD REST APIs for Employee",
        description = "CRUD REST APIs in Employee to CREATE, UPDATE, FETCH AND DELETE department details"
)
@RestController
@RequestMapping(path = "/api/employees", produces = {MediaType.APPLICATION_JSON_VALUE})
public class EmployeeController {

    private  EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(
            summary = "Fetch List of all employees Details REST API",
            description = "REST API to fetch List of all employees Details by ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employees);
    }

    @Operation(
            summary = "Fetch employee details REST API by ID",
            description = "REST API to fetch employees details by ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/fetchById")
    public ResponseEntity<EmployeeDto> getEmployeeById(Long id) {
        EmployeeDto dto = employeeService.getEmployeeById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dto);
    }

    @Operation(
            summary = "Fetch employee details REST API by name and last name",
            description = "REST API to fetch employees details by name and last name"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/fetchByName")
    public ResponseEntity<EmployeeDto> getEmployeeByNameAndLastName(
                    @NotEmpty @RequestParam String name,
                    @NotEmpty @RequestParam String lastName) {
        EmployeeDto dto = employeeService.getEmployeeByNameAndLastName(name, lastName);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dto);
    }

    @Operation(
            summary = "Fetch employee Details REST API by phone number",
            description = "REST API to fetch employees details by name"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/fetchByPhone")
    public ResponseEntity<EmployeeDto> getEmployeeByPhone(
                    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                    @Valid @RequestParam String phone) {
        EmployeeDto dto = employeeService.getEmployeeByPhone(phone);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dto);
    }

    @Operation(
            summary = "Create new Employee REST API",
            description = "REST API to create new Employee Details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })

    @PostMapping( "/createEmployee")
    public ResponseEntity<ResponseDto> createEmployee(@Valid @RequestBody EmployeeDto dto) {
        employeeService.createEmployee(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(ProjectConstants.STATUS_201, ProjectConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Update Employee Details REST API",
            description = "REST API to update Employee details based on a new EmployeeDto"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/updateEmployee")
    public ResponseEntity<ResponseDto> updateEmployee(@Valid @RequestBody EmployeeDto dto) {
        boolean updatedEmployee = employeeService.updateEmployee(dto);
        if (updatedEmployee) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(ProjectConstants.STATUS_200, ProjectConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(ProjectConstants.STATUS_417, ProjectConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete Employee REST API",
            description = "REST API to delete Employee based on id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteEmployee(@PathVariable Long id) {
        boolean isDeleted = employeeService.deleteEmployee(id);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(ProjectConstants.STATUS_200, ProjectConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(ProjectConstants.STATUS_417, ProjectConstants.MESSAGE_417_DELETE));
        }
    }
}