package com.sebin.employee_poc.service;

import com.sebin.employee_poc.entity.EmployeeEntity;
import com.sebin.employee_poc.exception.DepartmentNotFoundException;
import com.sebin.employee_poc.exception.EmployeeAlreadyExistsException;
import com.sebin.employee_poc.exception.EmployeeNotFoundException;
import com.sebin.employee_poc.exception.NoChangeInEmployeeException;
import com.sebin.employee_poc.model.ErrorResponse;
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
                employeeEntity.getLastName(),
                employeeEntity.getJobRole(),
                employeeEntity.getSalary(),
                employeeEntity.getDepartmentId()
        );
    }

    private boolean isEmployeeUnchanged(EmployeeEntity existingEmployee, EmployeeEntity newEmployee) {
        return existingEmployee.getFirstName().equals(newEmployee.getFirstName())
                && existingEmployee.getLastName().equals(newEmployee.getLastName())
                && existingEmployee.getJobRole().equals(newEmployee.getJobRole())
                && existingEmployee.getSalary() == newEmployee.getSalary()
                && existingEmployee.getDepartmentId() == newEmployee.getDepartmentId();
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

    public EmployeeResponse addEmployee(EmployeeEntity employeeEntity) {
        if (employeeRepository.employeeExists(employeeEntity.getFirstName(), employeeEntity.getLastName())) {
            throw new EmployeeAlreadyExistsException("Employee with the same name already exists.");
        }
        employeeRepository.save(employeeEntity);
        return mapToEmployeeResponse(employeeEntity);
    }

    public EmployeeResponse updateEmployee(int employeeId, EmployeeEntity employeeEntity) {
        EmployeeEntity existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id:" + employeeId));

        if (isEmployeeUnchanged(existingEmployee, employeeEntity)) {
            throw new NoChangeInEmployeeException("No changes detected for employee with id: " + employeeId);
        }

        existingEmployee.setFirstName(employeeEntity.getFirstName());
        existingEmployee.setLastName(employeeEntity.getLastName());
        existingEmployee.setJobRole(employeeEntity.getJobRole());
        existingEmployee.setSalary(employeeEntity.getSalary());
        existingEmployee.setDepartmentId(employeeEntity.getDepartmentId());

        employeeRepository.update(existingEmployee);
        return mapToEmployeeResponse(existingEmployee);
    }

    public ErrorResponse deleteEmployee(int employeeId) {
        employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id:" + employeeId));
        employeeRepository.delete(employeeId);
        return new ErrorResponse(200,"Employee deleted successfully");
    }
}
