package com.sebin.employee_poc.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepartmentResponse {
    public String departmentName;
    public String location;
}
