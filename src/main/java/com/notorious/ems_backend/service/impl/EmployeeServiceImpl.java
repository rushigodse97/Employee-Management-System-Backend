package com.notorious.ems_backend.service.impl;

import com.notorious.ems_backend.dto.EmployeeDto;
import com.notorious.ems_backend.entity.Employee;
import com.notorious.ems_backend.exception.DuplicateResourceException;
import com.notorious.ems_backend.exception.ResourceNotFoundException;
import com.notorious.ems_backend.mapper.EmployeeMapper;
import com.notorious.ems_backend.repository.EmployeeRepository;
import com.notorious.ems_backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        if (employeeRepository.existsByEmail(employeeDto.getEmail())) {
            throw new DuplicateResourceException(
                    "Email already exists: " + employeeDto.getEmail()
            );
        }
        Employee employee= EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee=employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
       Employee employee=employeeRepository.findById(employeeId)
               .orElseThrow(()->
                       new ResourceNotFoundException("Employee does not exist with given id: "+employeeId));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees=employeeRepository.findAll();
        return employees.stream().
                        map(EmployeeMapper::mapToEmployeeDto)
                            .collect(Collectors.toList());

        //Above Expression works same
        //Lambda Expression-> map((employee)->EmployeeMapper.mapToEmployeeDto(employee))
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
        Employee employee=employeeRepository.findById(employeeId).orElseThrow(
                ()-> new ResourceNotFoundException("Employee does not exist with id : "+employeeId)
        );
        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());

        Employee updatedemp=employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(updatedemp);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee=employeeRepository.findById(employeeId).orElseThrow(
                ()-> new ResourceNotFoundException("Employee does not exist with id : "+employeeId)
        );
        employeeRepository.deleteById(employeeId);
    }


}
