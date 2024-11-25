package com.sebin.employee_poc.repository;

import com.sebin.employee_poc.entity.EmployeeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class EmployeeRepositoryTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    @InjectMocks
    EmployeeRepository employeeRepository;

    @BeforeEach
    void setEmployeeRepository(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllTest(){
        EmployeeEntity employee1 = new EmployeeEntity(1, "Shyam","Prasad","HR",700000.0,3, LocalDateTime.now(),LocalDateTime.now());
        EmployeeEntity employee2 = new EmployeeEntity(2, "Priya","Ramesh","Analyst",700000.0,4, LocalDateTime.now(),LocalDateTime.now());
        List<EmployeeEntity> mockResponses = Arrays.asList(employee1,employee2);

        when(jdbcTemplate.query(eq("SELECT * FROM employee"),any(RowMapper.class))).thenReturn(mockResponses);

        List<EmployeeEntity> responses = employeeRepository.findAll();

        assertEquals(2,responses.size());
    }

    @Test
    void findByIdTest_employeeExists(){
        int employeeId = 1;
        EmployeeEntity mockEmployee = new EmployeeEntity(1, "Shyam","Prasad","HR",700000.0,3, LocalDateTime.now(),LocalDateTime.now());

        when(jdbcTemplate.query(eq("SELECT * FROM employee WHERE employeeId=1"), any(ResultSetExtractor.class)))
                .thenReturn(Optional.of(mockEmployee));

        Optional<EmployeeEntity> response = employeeRepository.findById(employeeId);

        assertEquals(mockEmployee,response.get());
    }

    @Test
    void findByIdTest_employeeDoesNotExist(){
        int employeeId = 1;
        when(jdbcTemplate.query(eq("SELECT * FROM employee WHERE employeeId=1"), any(ResultSetExtractor.class)))
                .thenReturn(Optional.empty());

        Optional<EmployeeEntity> response = employeeRepository.findById(employeeId);

        assertEquals(Optional.empty(),response);
    }

    @Test
    void employeeSaveTest(){
        EmployeeEntity mockEmployee = new EmployeeEntity(1, "Shyam","Prasad","HR",700000.0,3, LocalDateTime.now(),LocalDateTime.now());

        when(jdbcTemplate.update(
                eq("INSERT INTO employee(firstName,lastName,jobRole,salary,departmentId) VALUES(?,?,?,?,?)"),
                eq(mockEmployee.getFirstName()),
                eq(mockEmployee.getLastName()),
                eq(mockEmployee.getJobRole()),
                eq(mockEmployee.getSalary()),
                eq(mockEmployee.getDepartmentId())
                ))
                .thenReturn(1);

        EmployeeEntity response = employeeRepository.save(mockEmployee);

        assertEquals(mockEmployee,response);
    }

    @Test
    void employeeUpdateTest(){
        EmployeeEntity mockEmployee = new EmployeeEntity(1, "Shyam","Prasad","HR",700000.0,3, LocalDateTime.now(),LocalDateTime.now());

        when(jdbcTemplate.update(
                eq("UPDATE employee SET firstName=?,lastName=?,jobRole=?,salary=?,departmentId=? WHERE employeeId=?"),
                eq(mockEmployee.getFirstName()),
                eq(mockEmployee.getLastName()),
                eq(mockEmployee.getJobRole()),
                eq(mockEmployee.getSalary()),
                eq(mockEmployee.getDepartmentId()),
                eq(mockEmployee.getEmployeeId())
                ))
                .thenReturn(1);

        EmployeeEntity response = employeeRepository.update(mockEmployee);

        assertEquals(mockEmployee, response);
    }

    @Test
    void employeeDeleteTest(){
        int employeeId=1;

        when(jdbcTemplate.update(eq("DELETE FROM employee WHERE employeeId=1")))
                .thenReturn(1);

        int rowsAffected = employeeRepository.delete(employeeId);

        assertEquals(1,rowsAffected);
    }
}
