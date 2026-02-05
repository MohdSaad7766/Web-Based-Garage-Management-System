package com.aziz_motors.Web_Based.Garage.Management.System.exception;

public class CustomerWithEmailAlreadyRegisteredException extends RuntimeException {
    public CustomerWithEmailAlreadyRegisteredException(String message) {
        super(message);
    }

    public CustomerWithEmailAlreadyRegisteredException() {
        super("Provided email belongs to an another customer or the customer is already registered");
    }
}
