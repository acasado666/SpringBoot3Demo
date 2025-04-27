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

    /**
     * Retrieves all departments from the repository.
     *
     * @return a list of all departments in the form of DepartmentDto
     */
    public List<DepartmentDto> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Finds a department by its ID.
     *
     * @param id the ID of the department to find
     * @return the department with the given ID, or throw an EntityNotFoundException
     * if no such department exists
     */
    public DepartmentDto getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .map(departmentMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Department not found"));
    }

    /**
     * Creates a new department from the given DepartmentDto.
     * If a department with the same name already exists, it throws a DepartmentAlreadyExistsException.
     * It assigns a random ID to the new department and saves it.
     *
     * @param departmentDto the DepartmentDto containing the details of the new department
     * @return the newly created department as a DepartmentDto
     * @throws DepartmentAlreadyExistsException if a department with the same name already exists
     */
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        // Check if a department with the same name already exists
        boolean exists = departmentRepository.findDepartmentByNameIgnoreCase(departmentDto.getName()).isPresent();
        if (exists) {
            throw new DepartmentAlreadyExistsException("Department already exists");
        }

        Department newDepartment = departmentMapper.toEntity(departmentDto);

        // Generate a random ID for the new department
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newDepartment.setId(randomAccNumber);

        // Save the new department and return it as a DTO
        return departmentMapper.toDto(departmentRepository.save(newDepartment));
    }

    /**
     * Updates an existing department's details.
     *
     * @param departmentDto the updated details of the department
     * @return true if the department is updated successfully, false otherwise
     */
    public boolean updateDepartment(DepartmentDto departmentDto) {
        // Check if a department with the same name already exists
        boolean exists = departmentRepository.findDepartmentByNameIgnoreCase(departmentDto.getName()).isPresent();

        if (exists) {
            Department updated = departmentMapper.toEntity(departmentDto);

            System.out.println("updated = " + updated);

            departmentRepository.save(updated);
            return true;
        }
        throw new DepartmentAlreadyExistsException("Department does not exist, so it cannot be updated");
    }

    /**
     * Deletes a department by its ID.
     *
     * @param id the ID of the department to delete
     * @return true if the department is deleted successfully, false otherwise
     */
    public boolean deleteDepartment(Long id) {
        // Delete the department by ID
        departmentRepository.deleteById(id);
        return true;
    }
}
