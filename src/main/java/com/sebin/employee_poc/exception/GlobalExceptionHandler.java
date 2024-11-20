package com.sebin.employee_poc.exception;

import com.sebin.employee_poc.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDepartmentNotFound(DepartmentNotFoundException ex){
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(),ex.getMessage());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DepartmentAlreadyExists.class)
    public ResponseEntity<ErrorResponse> handleDepartmentAlreadyExists(DepartmentAlreadyExists ex){
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(),ex.getMessage());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEmployeeNotFound(EmployeeNotFoundException ex){
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmployeeAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmployeeAlreadyExists(EmployeeAlreadyExistsException ex){
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoChangeInEmployeeException.class)
    public ResponseEntity<ErrorResponse> handleNoChangeInEmployee(NoChangeInEmployeeException ex){
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex){
        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
