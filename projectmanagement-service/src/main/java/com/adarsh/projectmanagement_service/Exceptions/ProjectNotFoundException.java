package com.adarsh.projectmanagement_service.Exceptions;

public class ProjectNotFoundException extends RuntimeException{
    public ProjectNotFoundException(String m){
        super(m);
    }
}
