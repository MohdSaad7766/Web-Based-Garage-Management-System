package com.aziz_motors.Web_Based.Garage.Management.System.exception;

public class ResourceWithProvidedIdNotFoundException extends RuntimeException {
    public ResourceWithProvidedIdNotFoundException(String message) {
        super(message);
    }

    public ResourceWithProvidedIdNotFoundException() {
        super("Resource with provided id not found.");
    }
}
