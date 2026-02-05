package com.aziz_motors.Web_Based.Garage.Management.System.exception;

public class MechanicWithEmailAlreadyRegisteredException extends RuntimeException {
    public MechanicWithEmailAlreadyRegisteredException(String message) {
        super(message);
    }
    public MechanicWithEmailAlreadyRegisteredException() {
        super("Provided email belongs to an another mechanic or the mechanic is already registered");
    }
}
