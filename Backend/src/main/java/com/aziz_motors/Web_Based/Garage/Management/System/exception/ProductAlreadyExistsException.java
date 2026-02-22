package com.aziz_motors.Web_Based.Garage.Management.System.exception;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(String message) {
        super(message);
    }

    public ProductAlreadyExistsException() {
        super("Product already exists on the system.");
    }
}
