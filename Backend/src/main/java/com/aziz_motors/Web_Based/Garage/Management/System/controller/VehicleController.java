package com.aziz_motors.Web_Based.Garage.Management.System.controller;

import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.VehicleRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.GeneralMessageResponse;
import com.aziz_motors.Web_Based.Garage.Management.System.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    @Autowired
    VehicleController(VehicleService vehicleService){
        this.vehicleService = vehicleService;
    }

    @PostMapping("/add")
    public ResponseEntity<GeneralMessageResponse> addVehicle(@RequestParam UUID customerId, @RequestBody VehicleRequestDto dto){
        vehicleService.addVehicle(customerId, dto);

        return ResponseEntity.ok(new GeneralMessageResponse(true,"Vehicle Added Successfully..."));
    }
}
