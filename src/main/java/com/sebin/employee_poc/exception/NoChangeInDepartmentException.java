package com.sebin.employee_poc.exception;

public class NoChangeInDepartmentException extends RuntimeException{
    public NoChangeInDepartmentException(String message){
        super(message);
    }
}
