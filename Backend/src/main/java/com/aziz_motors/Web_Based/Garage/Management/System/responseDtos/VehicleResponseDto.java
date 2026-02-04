package com.aziz_motors.Web_Based.Garage.Management.System.responseDtos;

import com.aziz_motors.Web_Based.Garage.Management.System.enums.FuelType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class VehicleResponseDto {

    private String manufacturerName;

    private String modelName;

    private int modelYear;

    private String registrationNumber;

    private FuelType fuelType;
}
