package com.casado.sb3.controller;

import com.casado.sb3.constants.ProjectConstants;
import com.casado.sb3.dto.DepartmentDto;
import com.casado.sb3.dto.ErrorResponseDto;
import com.casado.sb3.dto.ResponseDto;
import com.casado.sb3.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "CRUD REST APIs for Department",
        description = "CRUD REST APIs in Department to CREATE, UPDATE, FETCH AND DELETE department details"
)
@RestController
@RequestMapping(path = "/api/departments", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;
    @Operation(
            summary = "Fetch List of departments Details REST API",
            description = "REST API to fetch List of departments Details by ID"
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
    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAll() {
//        return departmentService.getAllDepartments();
        List<DepartmentDto> departments = departmentService.getAllDepartments();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(departments);
    }
    @Operation(
            summary = "Fetch Department Details REST API",
            description = "REST API to fetch Department Details by ID"
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
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getById(@PathVariable Long id) {
//        return ResponseEntity.ok(departmentService.getDepartmentById(id));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(departmentService.getDepartmentById(id));
    }
    @Operation(
            summary = "Create Department REST API",
            description = "REST API to create new Department Details"
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
    @PostMapping
    public ResponseEntity<ResponseDto> create(@RequestBody DepartmentDto dto) {
//        return new ResponseEntity<>(departmentService.createDepartment(dto), HttpStatus.CREATED);
        departmentService.createDepartment(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(ProjectConstants.STATUS_201, ProjectConstants.MESSAGE_201));
    }
    @Operation(
            summary = "Update Department Details REST API",
            description = "REST API to update Department Details by ID"
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
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> update(@PathVariable Long id, @RequestBody DepartmentDto dto) {
//        return ResponseEntity.ok(departmentService.updateDepartment(id, dto));
        boolean isUpdated = departmentService.updateDepartment(id, dto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(ProjectConstants.STATUS_200, ProjectConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(ProjectConstants.STATUS_417, ProjectConstants.MESSAGE_417_UPDATE));
        }
    }
    @Operation(
            summary = "Delete Department Details REST API",
            description = "REST API to delete Department details based on id"
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
    public ResponseEntity<ResponseDto> delete(@PathVariable Long id) {
//        return ResponseEntity.noContent().build();
        boolean isDeleted = departmentService.deleteDepartment(id);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(ProjectConstants.STATUS_200, ProjectConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(ProjectConstants.STATUS_417, ProjectConstants.MESSAGE_417_DELETE));
        }
    }
}