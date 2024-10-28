package com.sebin.employee_poc.controller;

import com.sebin.employee_poc.model.EmployeeResponse;
import com.sebin.employee_poc.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEmployeesTest(){
        EmployeeResponse employee1 = new EmployeeResponse("Sebin","HR",700000,3);
        EmployeeResponse employee2 = new EmployeeResponse("Padmaprakash","Senior Software Engineer",1500000,1);
        List<EmployeeResponse> mockEmployees = Arrays.asList(employee1,employee2);

        when(employeeService.getAllEmployees()).thenReturn(mockEmployees);

        List<EmployeeResponse> responses= employeeController.getAllEmployees();

        assertEquals(2,responses.size());
        assertEquals(3,responses.get(0).getDepartmentId());
        assertEquals("Senior Software Engineer",responses.get(1).getJobRole());
    }
}
