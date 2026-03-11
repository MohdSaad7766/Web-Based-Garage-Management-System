package com.aziz_motors.Web_Based.Garage.Management.System.controller;

import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.MechanicRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.MechanicResponseDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.PaginatedResponse;
import com.aziz_motors.Web_Based.Garage.Management.System.service.MechanicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/mechanic")
public class MechanicController {

    private final MechanicService mechanicService;

    MechanicController(MechanicService mechanicService){
        this.mechanicService = mechanicService;
    }
    @PostMapping("/add")
    public ResponseEntity<UUID> addMechanic(@RequestBody MechanicRequestDto dto){
        return ResponseEntity.ok(mechanicService.addMechanic(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MechanicResponseDto> getMechanicById(@PathVariable UUID id){
        return ResponseEntity.ok(mechanicService.getMechanicResponseById(id));
    }

    @GetMapping("/get-all/{pageNo}")
    public ResponseEntity<PaginatedResponse<MechanicResponseDto>> getMechanics(
            @PathVariable int pageNo,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String mobileNumber,
            @RequestParam(required = false) Double minSalary,
            @RequestParam(required = false) Double maxSalary,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) LocalDate joinDate
    ){
        return ResponseEntity.ok(mechanicService.getMechanics(
                pageNo,
                name,
                email,
                mobileNumber,
                minSalary,
                maxSalary,
                address,
                joinDate
        ));
    }

    public void updateMechanic(){

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMechanic(@PathVariable UUID id){
        mechanicService.deleteMechanic(id);
        return ResponseEntity.ok("Mechanic with id"+id+ " has been deleted successful.");
    }
}
