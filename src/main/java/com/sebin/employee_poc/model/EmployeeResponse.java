package com.sebin.employee_poc.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EmployeeResponse {
    private String firstName;
    private String jobRole;
    private double salary;
    private int departmentId;
}
