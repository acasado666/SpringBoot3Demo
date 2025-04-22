package com.casado.sb3.dto;

import com.casado.sb3.annotation.CheckEmail;
import com.casado.sb3.annotation.CheckPhone;
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
        description = "Schema to hold Employee information"
)
public class EmployeeDto extends BaseDto {

    @Schema(
            description = "Name of the Employee", example = "Antonio"
    )
    @NotEmpty(message = "Name can not be a null or empty")
    @NotBlank(message = "Name is required")
    @Size(min = 5, max = 50, message = "Name must be 5–50 characters long")
    private String name;

    @Schema(
            description = "Name of the Employee", example = "Casado"
    )
    @NotEmpty(message = "Last Name can not be a null or empty")
    @NotBlank(message = "Last Name is required")
    @Size(min = 5, max = 50, message = "Name must be 5–50 characters long")
    private String lastName;

    @Schema(
            description = "Name of the Employee", example = "Casado"
    )
    @NotEmpty(message = "Address of the Employee can not be a null or empty")
    @NotBlank(message = "Address is required")
    @Size(min = 5, max = 50, message = "Name must be 5–50 characters long")
    private String address;

    @Schema(
            description = "Email of the Employee", example = "ac@example.com"
    )
    @CheckEmail
    @NotBlank(message = "Email is required")
    @Size(min = 5, max = 50, message = "Email must be 5–50 characters long")
    private String email;

    @CheckPhone
    @NotBlank(message = "Phone number is required")
    @Size(min = 9, max = 9, message = "Pho number must be exactly 9 digits long")
    private String phoneNumber;

    @Schema(
            description = "Name of the Department ID of the Employee", example = "1L"
    )
    @NotEmpty(message = "Name of the Department can not be a null or empty")
    @NotBlank(message = "Name of the Department is required")
    private Long departmentId;      // Needed for mapping

    @Schema(
            description = "Name of the Department", example = "Engineering"
    )
    @NotEmpty(message = "Name of the Department can not be a null or empty")
    @NotBlank(message = "Name of the Department is required")
    @Size(min = 5, max = 50, message = "Name must be 5–50 characters long")
    private String departmentName;  //  Optional read-only field
}