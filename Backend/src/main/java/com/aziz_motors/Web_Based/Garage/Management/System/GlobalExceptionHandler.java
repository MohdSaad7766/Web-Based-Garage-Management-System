package com.aziz_motors.Web_Based.Garage.Management.System;

import com.aziz_motors.Web_Based.Garage.Management.System.exception.CustomerEmailAlreadyRegisteredException;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.DuplicateAppointmentException;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.ResourceWithProvidedIdNotFoundException;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.VehicleAlreadyAssignedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateAppointmentException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateAppointment(
            DuplicateAppointmentException e,
            HttpServletRequest request
    ) {
        return buildResponse(e.getMessage(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(VehicleAlreadyAssignedException.class)
    public ResponseEntity<Map<String, Object>> handleVehicleAssigned(
            VehicleAlreadyAssignedException e,
            HttpServletRequest request
    ) {
        return buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ResourceWithProvidedIdNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleIdNotFoundException(
            ResourceWithProvidedIdNotFoundException e,
            HttpServletRequest request
    ) {
        return buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(CustomerEmailAlreadyRegisteredException.class)
    public ResponseEntity<Map<String, Object>> handleCustomerEmailAlreadyRegisteredException(
            CustomerEmailAlreadyRegisteredException e,
            HttpServletRequest request
    ) {
        return buildResponse(e.getMessage(), HttpStatus.BAD_REQUEST, request);
    }



    private ResponseEntity<Map<String, Object>> buildResponse(
            String message,
            HttpStatus status,
            HttpServletRequest request
    ) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("path", request.getRequestURI());

        return new ResponseEntity<>(body, status);
    }
}

