package com.notorious.ems_backend.service.impl;

import com.notorious.ems_backend.dto.EmployeeDto;
import com.notorious.ems_backend.entity.Employee;
import com.notorious.ems_backend.mapper.EmployeeMapper;
import com.notorious.ems_backend.repository.EmployeeRepository;
import com.notorious.ems_backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee= EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee=employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }
}
