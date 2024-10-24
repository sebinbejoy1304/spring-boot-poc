package com.sebin.employee_poc.service;

import com.sebin.employee_poc.entity.EmployeeEntity;
import com.sebin.employee_poc.exception.EmployeeNotFoundException;
import com.sebin.employee_poc.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public List<EmployeeEntity> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public EmployeeEntity getEmployeeById(int employeeId){
        return employeeRepository.findById(employeeId)
                .orElseThrow(()->new EmployeeNotFoundException("Employee not found with id:"+employeeId));
    }

    public int addEmployee(EmployeeEntity employeeEntity){
        return employeeRepository.save(employeeEntity);
    }

    public int updateEmployee(int employeeId){
        EmployeeEntity existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(()->new EmployeeNotFoundException("Employee not found with id:"+employeeId));
        existingEmployee.setSalary(0);
        return employeeRepository.update(existingEmployee);
    }

    public int deleteEmployee(int employeeId){
        employeeRepository.findById(employeeId)
                .orElseThrow(()->new EmployeeNotFoundException("Employee not found with id:"+employeeId));
        return employeeRepository.delete(employeeId);
    }
}
