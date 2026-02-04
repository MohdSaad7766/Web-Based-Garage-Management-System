package com.aziz_motors.Web_Based.Garage.Management.System.responseDtos;

import com.aziz_motors.Web_Based.Garage.Management.System.enums.AppointmentStatus;
import com.aziz_motors.Web_Based.Garage.Management.System.enums.FuelType;
import com.aziz_motors.Web_Based.Garage.Management.System.enums.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AppointmentResponseDto {

    private LocalDateTime dateTime;

    private ServiceType serviceType;

    private AppointmentStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private CustomerResponseDto customer;

    private VehicleResponseDto vehicle;
}
