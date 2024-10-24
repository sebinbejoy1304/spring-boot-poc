package com.sebin.employee_poc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepartmentEntity {
    private int departmentId;
    private String departmentName;
    private String location;
}
