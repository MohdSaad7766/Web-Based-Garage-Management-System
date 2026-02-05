package com.aziz_motors.Web_Based.Garage.Management.System.responseDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MechanicResponseDto {

    private UUID id;

    private String name;

    private String email;

    private String mobileNumber;

    private double salary;

    private String address;

    private LocalDate joinDate;
}
