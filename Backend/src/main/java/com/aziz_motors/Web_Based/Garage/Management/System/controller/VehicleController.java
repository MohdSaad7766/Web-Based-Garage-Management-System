package com.aziz_motors.Web_Based.Garage.Management.System.controller;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Vehicle;
import com.aziz_motors.Web_Based.Garage.Management.System.enums.FuelType;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.VehicleRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.VehicleUpdateRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.GeneralMessageResponse;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.PaginatedResponse;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.VehicleResponseDto;
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

        return ResponseEntity.ok(
                new GeneralMessageResponse(
                        true,
                        "Vehicle Added Successfully...")
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDto> getVehicleById(@PathVariable UUID id){
        return ResponseEntity.ok(vehicleService.getVehicleResponseById(id));
    }


    @GetMapping("/get-all/{pageNo}")
    public ResponseEntity<PaginatedResponse<VehicleResponseDto>> getVehicles(
            @PathVariable int pageNo,
            @RequestParam(required = false) String manufacturerName,
            @RequestParam(required = false) String modelName,
            @RequestParam(required = false) Integer modelYear,
            @RequestParam(required = false) String registrationNumber,
            @RequestParam(required = false) FuelType fuelType,
            @RequestParam(required = false) UUID customerId
    ){
        return ResponseEntity.ok(vehicleService.getVehicles(
                pageNo,
                manufacturerName,
                modelName,
                modelYear,
                registrationNumber,
                fuelType,
                customerId
        ));
    }



    @PutMapping
    public ResponseEntity<GeneralMessageResponse> updateVehicle(@RequestBody VehicleUpdateRequestDto dto){
        vehicleService.updateVehicle(dto);
        return ResponseEntity.ok(
                new GeneralMessageResponse(
                        true,
                        "Vehicle with id-"+dto.getId()+" has been deleted successful."
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralMessageResponse> deleteVehicle(@PathVariable UUID id){
        vehicleService.deleteVehicle(id);
        return ResponseEntity.ok(
                new GeneralMessageResponse(
                        true,
                        "Vehicle with id-"+id+" has been deleted successful."
                )
        );
    }
}
