package com.aziz_motors.Web_Based.Garage.Management.System.service;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Vehicle;
import com.aziz_motors.Web_Based.Garage.Management.System.repository.VehicleRepository;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.VehicleRequestDto;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

    private VehicleRepository vehicleRepository;

    VehicleService(VehicleRepository vehicleRepository){
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle findOrCreate(VehicleRequestDto dto){
        return vehicleRepository.findByRegistrationNumber(dto.getRegistrationNumber())
                .orElse(fromDto(dto));
    }

    public Vehicle fromDto(VehicleRequestDto dto){
        Vehicle vehicle = new Vehicle();

        vehicle.setManufacturerName(dto.getManufacturerName());
        vehicle.setModelName(dto.getModelName());
        vehicle.setModelYear(dto.getModelYear());
        vehicle.setFuelType(dto.getFuelType());
        vehicle.setRegistrationNumber(dto.getRegistrationNumber());

        return vehicle;
    }

}
