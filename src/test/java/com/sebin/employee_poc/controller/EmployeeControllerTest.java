package com.sebin.employee_poc.controller;

import com.sebin.employee_poc.entity.EmployeeEntity;
import com.sebin.employee_poc.model.ErrorResponse;
import com.sebin.employee_poc.model.EmployeeResponse;
import com.sebin.employee_poc.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setEmployeeController(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEmployeesTest(){
        EmployeeResponse employee1 = new EmployeeResponse("Shyam","Prasad","HR",700000.0,3,"HR","Kochi");
        EmployeeResponse employee2 = new EmployeeResponse("Priya","Kumari","Senior Software Engineer",1500000,1,"Software","Bangalore");
        List<EmployeeResponse> mockResponses = Arrays.asList(employee1,employee2);

        when(employeeService.getAllEmployees()).thenReturn(mockResponses);

        List<EmployeeResponse> responses= employeeController.getAllEmployees();

        assertEquals(2,responses.size());
        assertEquals(3,responses.get(0).getDepartmentId());
        assertEquals("Senior Software Engineer",responses.get(1).getJobRole());
    }

    @Test
    void addEmployeeTest(){
        EmployeeEntity employeeEntity = new EmployeeEntity(1,"Shyam","Prasad","Trainee",
                700000.0, 1, LocalDateTime.now(), LocalDateTime.now());
        EmployeeResponse employeeResponse = new EmployeeResponse("Shyam","Prasad","HR",700000.0,3,"HR","Kochi");

        when(employeeService.addEmployee(employeeEntity)).thenReturn(employeeResponse);

        EmployeeResponse response = employeeController.addEmployee(employeeEntity);

        assertEquals(employeeResponse, response);
    }

    @Test
    void updateEmployeeTest(){
        int employeeId=3;
        EmployeeEntity employeeEntity = new EmployeeEntity(1,"Shyam","Prasad","Trainee",
                700000.0, 1, LocalDateTime.now(), LocalDateTime.now());
        EmployeeResponse employeeResponse = new EmployeeResponse("Shyam","Prasad","HR",700000.0,3,"HR","Kochi");

        when(employeeService.updateEmployee(employeeId, employeeEntity)).thenReturn(employeeResponse);

        EmployeeResponse response = employeeController.updateEmployee(employeeId, employeeEntity);

        assertEquals(employeeResponse, response);
    }

    @Test
    void deleteEmployeeTest(){
        int employeeId=3;
        EmployeeEntity employeeEntity = new EmployeeEntity(1,"Shyam","Prasad","Trainee",
                700000.0, 1, LocalDateTime.now(), LocalDateTime.now());
        ErrorResponse mockResponse = new ErrorResponse(200, "Employee deleted successfully");

        when(employeeService.deleteEmployee(employeeId)).thenReturn(mockResponse);

        ErrorResponse response = employeeController.deleteEmployee(employeeId);

        assertEquals(mockResponse, response);
    }

    @Test
    void getEmployeeByIdTest(){
        int employeeId=3;
        EmployeeResponse mockResponse = new EmployeeResponse("Shyam","Prasad","HR",700000.0,3,"HR","Kochi");

        when(employeeService.getEmployeeById(employeeId)).thenReturn(mockResponse);

        EmployeeResponse employeeResponse = employeeController.getEmployeeById(employeeId);

        assertEquals(3,employeeResponse.getDepartmentId());
    }

    @Test
    void getEmployeesByDepartmentTest(){
        int departmentId = 2;
        EmployeeResponse employee1 = new EmployeeResponse("Shyam","Prasad","HR",700000.0,3,"HR","Kochi");
        EmployeeResponse employee2 = new EmployeeResponse("Priya","Kumari","Senior Software Engineer",1500000,1,"Software","Bangalore");
        List<EmployeeResponse> mockResponses = Arrays.asList(employee1,employee2);

        when(employeeService.getEmployeesByDepartment(departmentId)).thenReturn(mockResponses);

        List<EmployeeResponse> responses = employeeController.getEmployeesByDepartment(departmentId);

        assertEquals(1, responses.get(1).getDepartmentId());
    }
}