package com.sebin.employee_poc.exception;

public class NoChangeInEmployeeException extends RuntimeException{
    public NoChangeInEmployeeException(String message){
        super(message);
    }
}
