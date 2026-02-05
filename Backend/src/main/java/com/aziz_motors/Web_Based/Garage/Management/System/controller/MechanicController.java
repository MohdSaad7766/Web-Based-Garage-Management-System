package com.aziz_motors.Web_Based.Garage.Management.System.controller;

import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.MechanicRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.MechanicResponseDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.PaginatedResponse;
import com.aziz_motors.Web_Based.Garage.Management.System.service.MechanicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(mechanicService.getMechanicById(id));
    }

    @GetMapping("/get-all/{pageNo}")
    public ResponseEntity<PaginatedResponse<MechanicResponseDto>> getMechanics(@PathVariable int pageNo){
        return ResponseEntity.ok(mechanicService.getMechanics(pageNo));
    }

    public void updateMechanic(){

    }

    public void deleteMechanic(){

    }
}
