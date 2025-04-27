package com.casado.sb3.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
            description = "Department ID", example = "1L"
    )
    private Long id;

    @Schema(
            description = "Name of the Employee", example = "Antonio Casadò"
    )
    @NotEmpty(message = "Department name can not be a null or empty")
    @NotBlank(message = "Department name is required")
    @Size(min = 4, max = 50, message = "Name must be 5–50 characters long")
    private String name;

    @Schema(
            description = "List of the Employees", example = "Antonio Casadò, Fabian Schmidt"
    )
    private List<EmployeeDto> employees;
}