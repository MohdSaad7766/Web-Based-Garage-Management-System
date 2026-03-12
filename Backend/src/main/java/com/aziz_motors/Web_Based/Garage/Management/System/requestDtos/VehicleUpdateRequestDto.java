package com.aziz_motors.Web_Based.Garage.Management.System.requestDtos;

import com.aziz_motors.Web_Based.Garage.Management.System.enums.FuelType;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class VehicleUpdateRequestDto
{
    private UUID id;

    private String manufacturerName;

    private String modelName;

    private int modelYear;

    private String registrationNumber;

    private FuelType fuelType;
}
