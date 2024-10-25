package com.sebin.employee_poc.service;

import com.sebin.employee_poc.entity.EmployeeEntity;
import com.sebin.employee_poc.exception.DepartmentNotFoundException;
import com.sebin.employee_poc.exception.EmployeeNotFoundException;
import com.sebin.employee_poc.model.ApiResponse;
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
        return employeeRepository.findAll()
                .stream()
                .filter(employee->employee.getDepartmentId()==departmentId)
                .map(this::mapToEmployeeResponse)
                .collect(Collectors.toList());
    }

    public ApiResponse addEmployee(EmployeeEntity employeeEntity) {
        employeeRepository.save(employeeEntity);
        return new ApiResponse(200,"Employee Added successfully");
    }

    public ApiResponse updateEmployee(int employeeId) {
        EmployeeEntity existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id:" + employeeId));
        existingEmployee.setSalary(0);
        employeeRepository.update(existingEmployee);
        return new ApiResponse(200,"Employee updated successfully (set salary=0)");
    }

    public ApiResponse deleteEmployee(int employeeId) {
        employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id:" + employeeId));
        employeeRepository.delete(employeeId);
        return new ApiResponse(200,"Employee deleted successfully");
    }
}
