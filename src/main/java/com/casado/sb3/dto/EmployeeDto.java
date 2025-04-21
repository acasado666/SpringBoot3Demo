package com.casado.sb3.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Schema(
        name = "Employee",
        description = "Schema to hold Employee and Department information"
)
public class EmployeeDto extends BaseDto {

    @Schema(
            description = "Employee ID", example = "1L"
    )
    private Long id;

    @Schema(
            description = "Name of the Employee", example = "Antonio Casado"
    )
    @NotEmpty(message = "Name can not be a null or empty")
    @NotBlank(message = "Name is required")
    @Size(min = 4, max = 50, message = "Name must be 5–50 characters long")
    private String name;

    @Schema(
            description = "Name of the Department ID of the Employee", example = "1L"
    )
    private Long departmentId;      // Needed for mapping

    @Schema(
            description = "Name of the Department", example = "Engineering"
    )
    @NotEmpty(message = "Name of the Department can not be a null or empty")
    @NotBlank(message = "Name of the Department is required")
    @Size(min = 4, max = 50, message = "Name must be 5–50 characters long")
    private String departmentName;  //  Optional read-only field
}