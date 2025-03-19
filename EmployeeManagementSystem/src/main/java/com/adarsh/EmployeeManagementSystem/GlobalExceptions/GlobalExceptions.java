package com.adarsh.EmployeeManagementSystem.GlobalExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.adarsh.EmployeeManagementSystem.Exceptions.EmployeeFound;
import com.adarsh.EmployeeManagementSystem.Exceptions.EmployeeNotFoundException;
@ControllerAdvice

public class GlobalExceptions {
   @ExceptionHandler(EmployeeFound.class)
    public ResponseEntity<String> handleLeaveRequestNotFoundException(EmployeeFound ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.ALREADY_REPORTED);
    }
    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
