package com.casado.sb3.mapper;

import com.casado.sb3.dto.DepartmentDto;
import com.casado.sb3.entity.Department;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = EmployeeMapper.class)
public interface DepartmentMapper extends BaseMapper {

    DepartmentDto toDto(Department department);

    Department toEntity(DepartmentDto departmentDto);

    @AfterMapping
    default void enrichDto(@MappingTarget DepartmentDto dto, Department entity) {
        mapBaseFields(entity, dto);
    }

    @AfterMapping
    default void enrichEntity(@MappingTarget Department entity, DepartmentDto dto) {
        mapBaseFields(dto, entity);
    }
}