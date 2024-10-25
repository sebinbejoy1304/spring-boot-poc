package com.sebin.employee_poc.service;

import com.sebin.employee_poc.entity.EmployeeEntity;
import com.sebin.employee_poc.exception.DepartmentNotFoundException;
import com.sebin.employee_poc.exception.EmployeeNotFoundException;
import com.sebin.employee_poc.model.EmployeeResponse;
import com.sebin.employee_poc.repository.DepartmentRepository;
import com.sebin.employee_poc.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    private EmployeeResponse mapToEmployeeResponse(EmployeeEntity employeeEntity) {
        return new EmployeeResponse(
                employeeEntity.getFirstName(),
                employeeEntity.getJobRole(),
                employeeEntity.getSalary(),
                employeeEntity.getDepartmentId()
        );
    }

    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToEmployeeResponse)
                .collect(Collectors.toList());
    }

    public EmployeeResponse getEmployeeById(int employeeId) {
        return employeeRepository.findById(employeeId)
                .map(this::mapToEmployeeResponse)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id:" + employeeId));
    }

    public List<EmployeeResponse> getEmployeesByDepartment(int departmentId) {
        departmentRepository.findById(departmentId)
                .orElseThrow(()->new DepartmentNotFoundException("Department not found with id:"+departmentId));
        return employeeRepository.findEmployeesByDepartment(departmentId).stream()
                .map(this::mapToEmployeeResponse)
                .collect(Collectors.toList());
    }

    public int addEmployee(EmployeeEntity employeeEntity) {
        return employeeRepository.save(employeeEntity);
    }

    public int updateEmployee(int employeeId) {
        EmployeeEntity existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id:" + employeeId));
        existingEmployee.setSalary(0);
        return employeeRepository.update(existingEmployee);
    }

    public int deleteEmployee(int employeeId) {
        employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id:" + employeeId));
        return employeeRepository.delete(employeeId);
    }
}
