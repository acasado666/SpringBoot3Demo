package com.casado.sb3.service;

import com.casado.sb3.dto.EmployeeDto;
import com.casado.sb3.entity.Department;
import com.casado.sb3.entity.Employee;
import com.casado.sb3.mapper.EmployeeMapper;
import com.casado.sb3.repository.DepartmentRepository;
import com.casado.sb3.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    public List<EmployeeDto> getAllEmployees() {
        var all = employeeRepository.findAll();
        return employeeMapper.toDtoList(all);
    }

    public EmployeeDto getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
    }

    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        Optional<Employee> existing = employeeRepository.findById(employeeDto.getId());
        if (!existing.isPresent()) {
            Employee newEmployee = new Employee();
            newEmployee.setName(employeeDto.getName());
            Optional<Department> existsDepartment = departmentRepository.findById(employeeDto.getDepartmentId()); // employeeDto.getDepartmentId()

            if (existsDepartment.isPresent()) {
                newEmployee.setDepartment(existsDepartment.get()); // preserve existing relations if needed
                return employeeMapper.toDto(employeeRepository.save(newEmployee));
            } else {
                Department newDepartment = new Department();
                newDepartment.setName(employeeDto.getDepartmentName());
                departmentRepository.save(newDepartment);
                newEmployee.setDepartment(newDepartment); // preserve existing relations if needed
                return employeeMapper.toDto(employeeRepository.save(newEmployee));
            }
        }
        throw new EntityNotFoundException("Employee already exists");
    }

    public EmployeeDto updateEmployee(Long id, EmployeeDto dto) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        dto.setId(id); // make sure ID matches
        Employee updated = employeeMapper.toEntity(dto);
        updated.setDepartment(existing.getDepartment()); // preserve existing relations if needed

        return employeeMapper.toDto(employeeRepository.save(updated));
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
