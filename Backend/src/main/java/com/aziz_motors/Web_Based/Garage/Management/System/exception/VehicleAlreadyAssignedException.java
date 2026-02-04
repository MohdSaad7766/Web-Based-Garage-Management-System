package com.aziz_motors.Web_Based.Garage.Management.System.exception;

public class VehicleAlreadyAssignedException extends RuntimeException {
    public VehicleAlreadyAssignedException(String message) {
        super(message);
    }

    public VehicleAlreadyAssignedException() {

        super("Vehicle is already associated with another customer");
    }
}
