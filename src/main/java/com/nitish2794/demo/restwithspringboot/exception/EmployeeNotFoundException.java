package com.nitish2794.demo.restwithspringboot.exception;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Long id) {
        super("Could not find employee : " + id);
    }
}
