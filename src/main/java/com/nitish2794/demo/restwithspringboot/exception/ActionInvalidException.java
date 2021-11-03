package com.nitish2794.demo.restwithspringboot.exception;

public class ActionInvalidException extends RuntimeException {

    public ActionInvalidException(String action) {
        super("Invalid action : " + action);
    }
}
