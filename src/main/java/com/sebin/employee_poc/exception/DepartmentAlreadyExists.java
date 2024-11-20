package com.sebin.employee_poc.exception;

public class DepartmentAlreadyExists extends RuntimeException{
    public DepartmentAlreadyExists(String message){
        super(message);
    }
}
