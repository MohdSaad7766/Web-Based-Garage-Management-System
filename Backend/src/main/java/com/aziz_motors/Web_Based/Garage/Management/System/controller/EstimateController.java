package com.aziz_motors.Web_Based.Garage.Management.System.controller;

import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.EstimateRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.service.EstimateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/estimate")
public class EstimateController {

    private final EstimateService estimateService;

    EstimateController(EstimateService estimateService){
        this.estimateService = estimateService;
    }

    @PostMapping("/add")
    public ResponseEntity<UUID> addEstimate(@RequestBody EstimateRequestDto dto){
        return ResponseEntity.ok(estimateService.addEstimate(dto));
    }

    public void getEstimate(){

    }

    public void updateEstimate(){

    }

    public void deleteEstimate(){

    }
}
