package com.aziz_motors.Web_Based.Garage.Management.System.exception;

public class DuplicateAppointmentException extends RuntimeException {
    public DuplicateAppointmentException(String message) {
        super(message);
    }

    public DuplicateAppointmentException() {
        super("Appointment already exists for the given customer, vehicle, date and service");
    }
}
