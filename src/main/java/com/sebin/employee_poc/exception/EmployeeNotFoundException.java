package com.sebin.employee_poc.exception;

import com.sebin.employee_poc.repository.EmployeeRepository;

public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException(String message){
        super(message);
    }
}