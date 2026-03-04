package com.notorious.ems_backend.service;

import com.notorious.ems_backend.dto.EmployeeDto;
import org.springframework.stereotype.Service;

public interface EmployeeService {

    public EmployeeDto createEmployee(EmployeeDto employeeDto);
}
