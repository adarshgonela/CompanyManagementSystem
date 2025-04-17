package com.adarsh.projectmanagement_service.Exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String m) {
        super(m);
    }
}
