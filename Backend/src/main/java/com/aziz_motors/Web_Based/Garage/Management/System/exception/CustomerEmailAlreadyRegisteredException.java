package com.aziz_motors.Web_Based.Garage.Management.System.exception;

public class CustomerEmailAlreadyRegisteredException extends RuntimeException {
    public CustomerEmailAlreadyRegisteredException(String message) {
        super(message);
    }

    public CustomerEmailAlreadyRegisteredException() {
        super("Provided email belongs to an another customer or the customer is already registered");
    }
}
