package com.adarsh.LeaveManagementSystem.ExceptionHandlers;

public class InvalidLeaveRequestException extends RuntimeException{
    public InvalidLeaveRequestException(String message) {
        super("InvalidLeaveRequestException");
    }
}
