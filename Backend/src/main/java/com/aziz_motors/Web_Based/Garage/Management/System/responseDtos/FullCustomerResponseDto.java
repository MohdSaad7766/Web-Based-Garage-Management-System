package com.aziz_motors.Web_Based.Garage.Management.System.responseDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class FullCustomerResponseDto {
    private UUID id;

    private String name;

    private String email;

    private String mobileNumber;

    private String address;
    
    List<VehicleResponseDto> vehicles = new ArrayList<>();
    List<AppointmentResponseDto> appointments = new ArrayList<>();
    List<EstimateResponseDto> estimates = new ArrayList<>();
}
