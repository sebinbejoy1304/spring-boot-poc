package com.sebin.employee_poc.service;

import com.sebin.employee_poc.entity.DepartmentEntity;
import com.sebin.employee_poc.entity.EmployeeEntity;
import com.sebin.employee_poc.exception.EmployeeNotFoundException;
import com.sebin.employee_poc.exception.DepartmentNotFoundException;
import com.sebin.employee_poc.model.ApiResponse;
import com.sebin.employee_poc.model.EmployeeResponse;
import com.sebin.employee_poc.repository.DepartmentRepository;
import com.sebin.employee_poc.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    DepartmentRepository departmentRepository;

    @InjectMocks
    EmployeeService employeeService;

    @BeforeEach
    void setEmployeeService(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllEmployeesTest(){
        EmployeeEntity employee1 = new EmployeeEntity(1, "Shyam","Prasad","HR",700000,3, LocalDateTime.now(),LocalDateTime.now());
        EmployeeEntity employee2 = new EmployeeEntity(2, "Priya","Ramesh","Analyst",700000,4, LocalDateTime.now(),LocalDateTime.now());
        List<EmployeeEntity> mockResponses = Arrays.asList(employee1,employee2);

        when(employeeRepository.findAll()).thenReturn(mockResponses);

        List<EmployeeResponse> responses = employeeService.getAllEmployees();

        assertEquals(mockResponses.get(0).getFirstName(),responses.get(0).getFirstName());
        assertEquals(mockResponses.get(1).getJobRole(),responses.get(1).getJobRole());
    }

    @Test
    void getEmployeeById_employeeExists(){
        int employeeId = 5;
        EmployeeEntity mockEmployee = new EmployeeEntity(1, "Shyam","Prasad","HR",700000,3, LocalDateTime.now(),LocalDateTime.now());

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(mockEmployee));

        EmployeeResponse response = employeeService.getEmployeeById(employeeId);

        assertEquals(mockEmployee.getFirstName(), response.getFirstName());
    }

    @Test
    void getEmployeeById_employeeDoesNotExist(){
        int employeeId = 5;

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(employeeId));
    }

    @Test
    void getEmployeesByDepartment_departmentExists(){
        int departmentId = 3;
        EmployeeEntity employee1 = new EmployeeEntity(1, "Shyam","Prasad","HR",700000,3, LocalDateTime.now(),LocalDateTime.now());
        EmployeeEntity employee2 = new EmployeeEntity(2, "Priya","Ramesh","Analyst",700000,4, LocalDateTime.now(),LocalDateTime.now());
        EmployeeEntity employee3 = new EmployeeEntity(3, "Sathya","Shiva","Senior HR",1000000,3, LocalDateTime.now(),LocalDateTime.now());

        List<EmployeeEntity> mockResponses = Arrays.asList(employee1,employee2,employee3);

        when(departmentRepository.findById(departmentId))
                .thenReturn(Optional.of(new DepartmentEntity(3, "HR", "Kochi")));
        when(employeeRepository.findAll()).thenReturn(mockResponses);

        List<EmployeeResponse> response = employeeService.getEmployeesByDepartment(departmentId);

        assertEquals(2,response.size());
        assertEquals(employee1.getFirstName(),response.get(0).getFirstName());
    }

    @Test
    void getEmployeesByDepartment_departmentDoesNotExist(){
        int departmentId = 3;

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());

        assertThrows(DepartmentNotFoundException.class,()->employeeService.getEmployeesByDepartment(departmentId));
    }

    @Test
    void addEmployeeTest(){
        EmployeeEntity employeeEntity = new EmployeeEntity(1, "Shyam","Prasad","HR",700000,3, LocalDateTime.now(),LocalDateTime.now());

        when(employeeRepository.save(employeeEntity)).thenReturn(1);

        ApiResponse response = employeeService.addEmployee(employeeEntity);

        assertEquals(200,response.getCode());
    }

    @Test
    void updateEmployeeTest_employeeExists(){
        int employeeId=1;
        EmployeeEntity employeeEntity = new EmployeeEntity(1, "Shyam","Prasad","HR",700000,3, LocalDateTime.now(),LocalDateTime.now());

        when(employeeRepository.findById(employeeId))
                .thenReturn(Optional.of(new EmployeeEntity(1, "Shyam","Prasad","HR",700000,3, LocalDateTime.now(),LocalDateTime.now())));
        when(employeeRepository.update(employeeEntity))
                .thenReturn(1);

        ApiResponse response = employeeService.updateEmployee(employeeId,employeeEntity);

        assertEquals(200,response.getCode());
    }

    @Test
    void updateEmployeeTest_employeeDoesNotExist(){
        int employeeId=1;
        EmployeeEntity employeeEntity = new EmployeeEntity(1, "Shyam","Prasad","HR",700000,3, LocalDateTime.now(),LocalDateTime.now());

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
        when(employeeRepository.update(employeeEntity)).thenReturn(1);

        assertThrows(EmployeeNotFoundException.class,()->employeeService.updateEmployee(employeeId,employeeEntity));
    }

    @Test
    void getEmployeeCountTest(){
        EmployeeEntity employee1 = new EmployeeEntity(1, "Shyam","Prasad","HR",700000,3, LocalDateTime.now(),LocalDateTime.now());
        EmployeeEntity employee2 = new EmployeeEntity(2, "Priya","Ramesh","Analyst",700000,4, LocalDateTime.now(),LocalDateTime.now());
        EmployeeEntity employee3 = new EmployeeEntity(3, "Sathya","Shiva","Senior HR",1000000,3, LocalDateTime.now(),LocalDateTime.now());

        List<EmployeeEntity> mockResponses = Arrays.asList(employee1,employee2,employee3);

        when(employeeRepository.findAll()).thenReturn(mockResponses);

        long response = employeeService.getEmployeeCount();

        assertEquals(3,response);
    }

    @Test
    void getEmployeeCountByDepartmentTest(){
        int departmentId=3;
        EmployeeEntity employee1 = new EmployeeEntity(1, "Shyam","Prasad","HR",700000,3, LocalDateTime.now(),LocalDateTime.now());
        EmployeeEntity employee2 = new EmployeeEntity(2, "Priya","Ramesh","Analyst",700000,4, LocalDateTime.now(),LocalDateTime.now());
        EmployeeEntity employee3 = new EmployeeEntity(3, "Sathya","Shiva","Senior HR",1000000,3, LocalDateTime.now(),LocalDateTime.now());

        List<EmployeeEntity> mockResponses = Arrays.asList(employee1,employee2,employee3);

        when(employeeRepository.findAll()).thenReturn(mockResponses);

        long response = employeeService.getEmployeeCountByDepartment(departmentId);

        assertEquals(2,response);
    }

    @Test
    void getTotalEmployeeSalary(){
        EmployeeEntity employee1 = new EmployeeEntity(1, "Shyam","Prasad","HR",700000,3, LocalDateTime.now(),LocalDateTime.now());
        EmployeeEntity employee2 = new EmployeeEntity(2, "Priya","Ramesh","Analyst",700000,4, LocalDateTime.now(),LocalDateTime.now());
        EmployeeEntity employee3 = new EmployeeEntity(3, "Sathya","Shiva","Senior HR",1000000,3, LocalDateTime.now(),LocalDateTime.now());

        List<EmployeeEntity> mockResponses = Arrays.asList(employee1,employee2,employee3);

        when(employeeRepository.findAll()).thenReturn(mockResponses);

        String response = employeeService.getTotalEmployeeSalary();

        assertEquals("2400000.00",response);
    }

    @Test
    void getTotalEmployeeSalaryByDepartment_departmentExists(){
        int departmentId = 3;
        EmployeeEntity employee1 = new EmployeeEntity(1, "Shyam","Prasad","HR",700000,3, LocalDateTime.now(),LocalDateTime.now());
        EmployeeEntity employee2 = new EmployeeEntity(2, "Priya","Ramesh","Analyst",700000,4, LocalDateTime.now(),LocalDateTime.now());
        EmployeeEntity employee3 = new EmployeeEntity(3, "Sathya","Shiva","Senior HR",1000000,3, LocalDateTime.now(),LocalDateTime.now());

        List<EmployeeEntity> mockResponses = Arrays.asList(employee1,employee2,employee3);

        when(departmentRepository.findById(departmentId))
                .thenReturn(Optional.of(new DepartmentEntity(3, "HR", "Kochi")));
        when(employeeRepository.findAll()).thenReturn(mockResponses);

        String response = employeeService.getTotalEmployeeSalaryByDepartment(departmentId);

        assertEquals("1700000.00",response);
    }

    @Test
    void getTotalEmployeeSalaryByDepartment_departmentDoesNotExist(){
        int departmentId = 3;
        EmployeeEntity employee1 = new EmployeeEntity(1, "Shyam","Prasad","HR",700000,3, LocalDateTime.now(),LocalDateTime.now());
        EmployeeEntity employee2 = new EmployeeEntity(2, "Priya","Ramesh","Analyst",700000,4, LocalDateTime.now(),LocalDateTime.now());
        EmployeeEntity employee3 = new EmployeeEntity(3, "Sathya","Shiva","Senior HR",1000000,3, LocalDateTime.now(),LocalDateTime.now());

        List<EmployeeEntity> mockResponses = Arrays.asList(employee1,employee2,employee3);

        when(departmentRepository.findById(departmentId))
                .thenReturn(Optional.empty());
        when(employeeRepository.findAll()).thenReturn(mockResponses);

        assertThrows(DepartmentNotFoundException.class,()->employeeService.getTotalEmployeeSalaryByDepartment(departmentId));
    }
}
