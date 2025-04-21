package com.casado.sb3.mapper;


import com.casado.sb3.dto.BaseDto;
import com.casado.sb3.entity.BaseEntity;

public interface BaseMapper {

    default void mapBaseFields(BaseEntity entity, BaseDto dto) {
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setUpdatedBy(entity.getUpdatedBy());
    }

    default void mapBaseFields(BaseDto dto, BaseEntity entity) {
        // Normally these are managed by JPA, but map back if needed
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setUpdatedAt(dto.getUpdatedAt());
        entity.setUpdatedBy(dto.getUpdatedBy());
    }
}