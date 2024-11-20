package com.sebin.employee_poc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class EmployeeEntity {
    private int employeeId;
    private String firstName;
    private String lastName;
    private String jobRole;
    private Double salary;
    private Integer departmentId;
    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;
}
