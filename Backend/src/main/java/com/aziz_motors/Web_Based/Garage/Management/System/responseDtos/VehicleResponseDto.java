package com.aziz_motors.Web_Based.Garage.Management.System.responseDtos;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Vehicle;
import com.aziz_motors.Web_Based.Garage.Management.System.enums.FuelType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class VehicleResponseDto {

    private UUID vehicleId;

    private String manufacturerName;

    private String modelName;

    private int modelYear;

    private String registrationNumber;

    private FuelType fuelType;

    private UUID customerId;

    public VehicleResponseDto(Vehicle vehicle){
        this.setVehicleId(vehicle.getId());
        this.setManufacturerName(vehicle.getManufacturerName());
        this.setModelName(vehicle.getModelName());
        this.setModelYear(vehicle.getModelYear());
        this.setRegistrationNumber(vehicle.getRegistrationNumber());
        this.setFuelType(vehicle.getFuelType());
        this.setCustomerId(vehicle.getCustomer().getId());
    }
}
