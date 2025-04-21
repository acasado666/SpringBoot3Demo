package com.casado.sb3.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public abstract class BaseDto {

    @Schema(
            description = "Auditing creation time", example = "2025-04-18T23:17:53.525231"
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Auditing creation by", example = "Antonio Casadò"
    )
    private String createdBy;

    @Schema(
            description = "Auditing updated time", example = "2025-04-18T23:17:53.525231"
    )
    private LocalDateTime updatedAt;

    @Schema(
            description = "Auditing updated by", example = "Antonio Casadò"
    )
    private String updatedBy;
}