package com.sebin.employee_poc.service;

import com.sebin.employee_poc.entity.DepartmentEntity;
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

        DepartmentEntity departmentEntity = departmentRepository.findById(employeeEntity.getDepartmentId())
                .orElseThrow(()->new DepartmentNotFoundException("Department not found with id: "+employeeEntity.getDepartmentId()));

        return new EmployeeResponse(
                employeeEntity.getFirstName(),
                employeeEntity.getLastName(),
                employeeEntity.getJobRole(),
                employeeEntity.getSalary(),
                employeeEntity.getDepartmentId(),
                departmentEntity.getDepartmentName(),
                departmentEntity.getLocation()
        );
    }

    private boolean isEmployeeUnchanged(EmployeeEntity existingEmployee, EmployeeEntity newEmployee) {
        return (newEmployee.getFirstName()==null || existingEmployee.getFirstName().equals(newEmployee.getFirstName()))
                && (newEmployee.getLastName()==null || existingEmployee.getLastName().equals(newEmployee.getLastName()))
                && (newEmployee.getJobRole() == null || existingEmployee.getJobRole().equals(newEmployee.getJobRole()))
                && (newEmployee.getSalary() == null || existingEmployee.getSalary() == newEmployee.getSalary()
                && (newEmployee.getDepartmentId() == null || existingEmployee.getDepartmentId() == newEmployee.getDepartmentId()));
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

        if (isEmployeeUnchanged(existingEmployee, employeeEntity))
            throw new NoChangeInEmployeeException("No changes detected for employee with id: " + employeeId);

        if (employeeEntity.getFirstName() != null)
            existingEmployee.setFirstName(employeeEntity.getFirstName());
        if (employeeEntity.getLastName() != null)
            existingEmployee.setLastName(employeeEntity.getLastName());
        if (employeeEntity.getJobRole() != null)
            existingEmployee.setJobRole(employeeEntity.getJobRole());
        if (employeeEntity.getSalary() != null)
            existingEmployee.setSalary(employeeEntity.getSalary());
        if (employeeEntity.getDepartmentId() != null)
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
