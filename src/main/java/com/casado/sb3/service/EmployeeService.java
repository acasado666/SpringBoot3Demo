package com.casado.sb3.service;

import com.casado.sb3.constants.ProjectConstants;
import com.casado.sb3.dto.EmployeeDto;
import com.casado.sb3.entity.Department;
import com.casado.sb3.entity.Employee;
import com.casado.sb3.exception.EmployeeAlreadyExistsException;
import com.casado.sb3.exception.ResourceNotFoundException;
import com.casado.sb3.mapper.EmployeeMapper;
import com.casado.sb3.repository.DepartmentRepository;
import com.casado.sb3.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;
    private EmployeeMapper employeeMapper;


    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.employeeMapper = employeeMapper;
    }

    /**
     * Retrieve all employees as a list of EmployeeDto.
     *
     * @return a list of all employees in the form of EmployeeDto
     */
    public List<EmployeeDto> getAllEmployees() {
        // Fetch all employees from the repository
        var all = employeeRepository.findAll();

        // Convert the list of Employee entities to a list of EmployeeDto
        return employeeMapper.toDtoList(all);
    }

    /**
     * Find an employee by their ID.
     *
     * @param id the ID of the employee to find
     * @return the employee with the given ID, or throw an ResourceNotFoundException
     * if no such employee exists
     */
    public EmployeeDto getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", "With given id employee not found"));
    }

    /**
     * Retrieve an employee by their name and last name.
     *
     * @param name     the name of the employee
     * @param lastName the last name of the employee
     * @return the employee with the given name and last name as an EmployeeDto
     * @throws ResourceNotFoundException if no such employee exists
     */
    public EmployeeDto getEmployeeByNameAndLastName(String name, String lastName) {
        // Trim the input parameters to remove any leading or trailing spaces
        String trimmedName = name.trim();
        String trimmedLastName = lastName.trim();

        // Search for the employee in the repository using the trimmed name and last name, ignoring case
        return employeeRepository.findByNameAndLastNameIgnoreCase(trimmedName, trimmedLastName)
                // Map the found Employee entity to an EmployeeDto
                .map(employeeMapper::toDto)
                // Throw an exception if no employee is found
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "name and lastname", "With given name and lastname, employee not found"));
    }

    /**
     * Retrieve an employee by their phone number.
     *
     * @param phoneNumber the phone number of the employee
     * @return the employee with the given phone number as an EmployeeDto
     * @throws ResourceNotFoundException if no such employee exists
     */
    public EmployeeDto getEmployeeByPhone(String phoneNumber) {
        return employeeRepository.findByPhoneNumber(phoneNumber)
                .map(employeeMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "phoneNumber", "With given phoneNumber, employee not found"));
    }

    /**
     * Creates a new employee from the given EmployeeDto.
     * If an employee with the same phone number already exists, it throws an EntityNotFoundException.
     * If the given department name does not exist, it creates a new department and uses it.
     * If the given department name exists, it uses the existing one.
     * It then saves the new employee and returns it as an EmployeeDto.
     *
     * @param employeeDto the EmployeeDto containing the details of the new employee
     * @return the newly created employee as an EmployeeDto
     * @throws EntityNotFoundException if an employee with the same phone number already exists
     */
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        // Check if an employee with the same phone number already exists
        // If it does, throw an EntityNotFoundException

        var exists = employeeRepository.findByPhoneNumber(employeeDto.getPhoneNumber()).isPresent();

        if (exists) throw new EmployeeAlreadyExistsException("Employee already exists");

        // Fetch the department by name, if it does not exist, create a new one
        // This is done to avoid creating multiple departments with the same name
        Optional<Department> department = departmentRepository.findDepartmentByNameIgnoreCase(departmentPicker());
//        employeeDto.setDepartmentId(department.getId());
//        employeeDto.setDepartmentName(department.getName());
        Employee newEmployee = employeeMapper.toEntity(employeeDto);
        newEmployee.setDepartment(department.get());
        employeeRepository.save(newEmployee);
        return employeeMapper.toDto(newEmployee);
    }

    /**
     * Update an existing employee's details.
     *
     * @param employeeDto the updated details of the employee
     * @return the updated employee in the form of an EmployeeDto
     * @throws ResourceNotFoundException if no employee with the given ID is found
     */
    public boolean updateEmployee(EmployeeDto employeeDto) {
        // Fetch the existing employee by ID, throw exception if not found
        // This is done to preserve the existing department relationship
        Employee exists = employeeRepository.findByPhoneNumber(employeeDto.getPhoneNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "phoneNumber", "With given phoneNumber, employee not found"));

        // Update the employee's details from the DTO
        Employee updated = employeeMapper.toEntity(employeeDto);

        // Preserve existing department relationship
        // This is done to avoid deleting the department entity if it is
        // not explicitly updated in the DTO
        updated.setDepartment(exists.getDepartment());

        // Save the updated employee entity and convert it back to DTO
        employeeRepository.save(updated);
        return true;
    }

    /**
     * Delete an employee by ID.
     *
     * @param id the ID of the employee to delete
     * @return a boolean indicating if the employee was successfully deleted
     * @throws ResourceNotFoundException if no employee with the given ID is found
     */
    public boolean deleteEmployee(Long id) {
        // Attempt to find the employee by the given ID
        Employee exists = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", "With given id Employee not found"));

        // Delete the employee by ID
        employeeRepository.deleteById(id);

        // Return true indicating the deletion was successful
        return true;
    }

    /**
     * Return a random department name from the list of pre-defined departments.
     *
     * @return a random department name
     */
    private String departmentPicker() {
        // Pick a random number between 0 and 3
        List<String> departments = Arrays.asList(ProjectConstants.ENGINEERING,
                                                 ProjectConstants.HUMAN_RESOURCES,
                                                 ProjectConstants.ACCOUNTING,
                                                 ProjectConstants.COMPUTER_SCIENCE);
        final Random random = new Random();
        int index = random.nextInt(departments.size());

        return departments.get(index);
    }
}
