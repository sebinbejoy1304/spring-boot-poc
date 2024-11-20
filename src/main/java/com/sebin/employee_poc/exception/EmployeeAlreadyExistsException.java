package com.sebin.employee_poc.exception;

public class EmployeeAlreadyExistsException extends RuntimeException{
    public EmployeeAlreadyExistsException(String msg){
        super(msg);
    }
}
