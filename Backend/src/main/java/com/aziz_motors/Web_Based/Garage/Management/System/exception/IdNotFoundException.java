package com.aziz_motors.Web_Based.Garage.Management.System.exception;

public class IdNotFoundException extends RuntimeException {
    public IdNotFoundException(String message) {
        super(message);
    }

    public IdNotFoundException() {
        super("Resource with provided id not found.");
    }
}
