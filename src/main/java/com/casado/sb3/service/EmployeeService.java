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

    public EmployeeDto getEmployeeByNameAndLastName(String name, String lastName) {
        return employeeRepository.findByNameAndLastNameIgnoreCase(name.trim(), lastName.trim())
                .map(employeeMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
    }

    public EmployeeDto getEmployeeByPhone(String phoneNumber) {
        return employeeRepository.findByPhoneNumber(phoneNumber)
                .map(employeeMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
    }

    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        Optional<Employee> existing = employeeRepository.findByNameAndLastNameIgnoreCase(employeeDto.getName(), employeeDto.getLastName());
        if (!existing.isPresent()) {
            Employee newEmployee = new Employee();
            newEmployee.setName(employeeDto.getName());
            newEmployee.setEmail(employeeDto.getEmail());
            Optional<Department> existsDepartment = departmentRepository.findById(employeeDto.getDepartmentId());

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

    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        Employee existing = employeeRepository.findByNameAndLastNameIgnoreCase(employeeDto.getName(), employeeDto.getLastName())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        Employee updated = employeeMapper.toEntity(employeeDto);
        updated.setDepartment(existing.getDepartment()); // preserve existing relations if needed
        return employeeMapper.toDto(employeeRepository.save(updated));
    }

    public boolean deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
        return true;
    }
}
