package com.aziz_motors.Web_Based.Garage.Management.System.exception;

public class DealerAlreadyExistsException extends RuntimeException {
    public DealerAlreadyExistsException(String message) {
        super(message);
    }

    public DealerAlreadyExistsException() {
        super("Dealer with name or email or gst number already exists.");
    }

}
