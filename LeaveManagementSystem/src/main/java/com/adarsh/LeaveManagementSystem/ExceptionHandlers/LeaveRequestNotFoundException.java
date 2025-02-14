package com.adarsh.LeaveManagementSystem.ExceptionHandlers;

public class LeaveRequestNotFoundException extends RuntimeException{
    public LeaveRequestNotFoundException(String message) {
        super("leave Request not found");
    }
}
