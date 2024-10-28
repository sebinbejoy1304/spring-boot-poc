package com.sebin.employee_poc.controller;

import com.sebin.employee_poc.entity.EmployeeEntity;
import com.sebin.employee_poc.model.ApiResponse;
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
    public ApiResponse addEmployee(@RequestBody EmployeeEntity employeeEntity){
        return employeeService.addEmployee(employeeEntity);
    }

    @PutMapping("employee/{employeeId}")
    public ApiResponse updateEmployee(@PathVariable int employeeId, @RequestBody EmployeeEntity employeeEntity){
        return employeeService.updateEmployee(employeeId, employeeEntity);
    }

    @DeleteMapping("employee/{employeeId}")
    public ApiResponse deleteEmployee(@PathVariable int employeeId){
        return employeeService.deleteEmployee(employeeId);
    }

    @GetMapping("employees/count")
    public double getEmployeeCount(){
        return employeeService.getEmployeeCount();
    }

    @GetMapping("department/employeeCount/{departmentId}")
    public double getEmployeeCountByDepartment(@PathVariable int departmentId){
        return employeeService.getEmployeeCountByDepartment(departmentId);
    }

    @GetMapping("employees/totalSalary")
    public String getTotalEmployeeSalary(){
        return employeeService.getTotalEmployeeSalary();
    }

    @GetMapping("department/totalSalary/{departmentId}")
    public String getTotalEmployeeSalaryByDepartment(@PathVariable int departmentId){
        return employeeService.getTotalEmployeeSalaryByDepartment(departmentId);
    }

    @GetMapping("department/averageSalary/{departmentId}")
    public double getAverageSalaryInDepartment(@PathVariable int departmentId){
        return employeeService.getAverageSalaryInDepartment(departmentId);
    }

    @GetMapping("employees/maxSalary")
    public EmployeeResponse getMaxSalary(){
        return employeeService.getEmployeeWithMaxSalary();
    }

}
