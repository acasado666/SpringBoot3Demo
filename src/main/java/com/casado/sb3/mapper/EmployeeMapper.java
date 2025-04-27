package com.casado.sb3.mapper;

import com.casado.sb3.dto.EmployeeDto;
import com.casado.sb3.entity.Employee;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper extends BaseMapper {

    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName") // Map nested FK
    EmployeeDto toDto(Employee employee);

    @Mapping(source = "departmentId", target = "department.id")  // optional, or handle manually
    Employee toEntity(EmployeeDto employeeDto);

    List<EmployeeDto> toDtoList(List<Employee> employees);

    @AfterMapping
    default void enrichDto(@MappingTarget EmployeeDto dto, Employee entity) {
        mapBaseFields(entity, dto);
    }

    @AfterMapping
    default void enrichEntity(@MappingTarget Employee entity, EmployeeDto dto) {
        mapBaseFields(dto, entity);
    }
}