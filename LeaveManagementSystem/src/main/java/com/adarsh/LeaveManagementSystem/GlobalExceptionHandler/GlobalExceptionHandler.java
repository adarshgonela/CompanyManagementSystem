package com.adarsh.LeaveManagementSystem.GlobalExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.adarsh.LeaveManagementSystem.ExceptionHandlers.InvalidLeaveRequestException;
import com.adarsh.LeaveManagementSystem.ExceptionHandlers.LeaveRequestNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LeaveRequestNotFoundException.class)
    public ResponseEntity<String> handleLeaveRequestNotFoundException(LeaveRequestNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidLeaveRequestException.class)
    public ResponseEntity<String> handleInvalidLeaveRequestException(InvalidLeaveRequestException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
