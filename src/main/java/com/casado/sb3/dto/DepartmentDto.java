package com.casado.sb3.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@Schema(
        name = "Department",
        description = "Schema to hold Department information"
)
public class DepartmentDto extends BaseDto {

    @Schema(
            description = "Name of the Department", example = "Computer Science"
    )
    @NotEmpty(message = "Department name can not be a null or empty")
    @NotBlank(message = "Department name is required")
    @Size(min = 5, max = 50, message = "Name must be 5–50 characters long")
    private String name;


    @Schema(
            description = "Department Code inside campus", example = "CS_101"
    )
    @NotEmpty(message = "Department Code can not be a null or empty")
    @NotBlank(message = "Department Code is required")
    @Size(min = 5, max = 50, message = "Name must be 6–10 characters long")
    @Column(name = "department_code", unique = true)
    private String departmentCode;

    @Schema(
            description = "Description of the Department", example = "Antonio Casadò"
    )
    @NotEmpty(message = "Department description can not be a null or empty")
    @NotBlank(message = "Department description is required")
    @Size(min = 5, max = 50, message = "Name must be 10–300 characters long")
    private String description;

    @Schema(
            description = "Building location", example = "ZH1_23"
    )
    @NotEmpty(message = "Department building location can not be a null or empty")
    @NotBlank(message = "Department building location is required")
    @Size(min = 5, max = 50, message = "Where is the building located inside campus")
    private String building;

    @Schema(
            description = "Mobile Number of the customer", example = "9345432123"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String phoneNumber;

    @Schema(
            description = "List of the Employees", example = "Antonio Casadò, Fabian Schmidt"
    )
    private List<EmployeeDto> employees;
}