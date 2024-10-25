package com.sebin.employee_poc.controller;

import com.sebin.employee_poc.entity.EmployeeEntity;
import com.sebin.employee_poc.model.EmployeeResponse;
import com.sebin.employee_poc.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("employees")
    public List<EmployeeResponse> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @GetMapping("employee/{employeeId}")
    public EmployeeResponse getEmployeeById(@PathVariable int employeeId){
        return employeeService.getEmployeeById(employeeId);
    }

    @GetMapping("employee/department/{departmentId}")
    public List<EmployeeResponse> getEmployeesByDepartment(@PathVariable int departmentId){
        return employeeService.getEmployeesByDepartment(departmentId);
    }

    @PostMapping("employee/add")
    public int addEmployee(@RequestBody EmployeeEntity employeeEntity){
        return employeeService.addEmployee(employeeEntity);
    }

    @PutMapping("employee/{employeeId}")
    public int updateEmployee(@PathVariable int employeeId){
        return employeeService.updateEmployee(employeeId);
    }

    @DeleteMapping("employee/{employeeId}")
    public int deleteEmployee(@PathVariable int employeeId){
        return employeeService.deleteEmployee(employeeId);
    }
}
