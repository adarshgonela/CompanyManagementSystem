package com.adarsh.projectmanagement_service.Exceptions;
public class EmployeeNotFound  extends RuntimeException{
    public EmployeeNotFound(String msg){
        super(msg);
    }
}
