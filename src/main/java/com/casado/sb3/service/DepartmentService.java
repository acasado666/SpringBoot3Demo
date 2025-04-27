package com.casado.sb3.service;

import com.casado.sb3.dto.DepartmentDto;
import com.casado.sb3.entity.Department;
import com.casado.sb3.exception.DepartmentAlreadyExistsException;
import com.casado.sb3.mapper.DepartmentMapper;
import com.casado.sb3.repository.DepartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public List<DepartmentDto> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toDto)
                .collect(Collectors.toList());
    }

    public DepartmentDto getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .map(departmentMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));
    }

    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        boolean exists = departmentRepository.findById(departmentDto.getId()).isPresent();
        if (exists) throw new DepartmentAlreadyExistsException("Department already exists");

        Department newDepartment = departmentMapper.toEntity(departmentDto);
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newDepartment.setId(randomAccNumber);
        return departmentMapper.toDto(departmentRepository.save(newDepartment));
    }

    public boolean updateDepartment(Long id, DepartmentDto dto) {
        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));

        dto.setId(id);
        Department updated = departmentMapper.toEntity(dto);
        departmentRepository.save(updated);
        return true;
    }

    public boolean deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
        return true;
    }
}
