package com.aziz_motors.Web_Based.Garage.Management.System.controller;

import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.DealerRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.service.DealerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/dealer")
public class DealerController {

    private final DealerService dealerService;

    public DealerController(DealerService dealerService){
        this.dealerService = dealerService;
    }

    @PostMapping("/add")
    public ResponseEntity<UUID> addDealer(@RequestBody DealerRequestDto dto){
        return ResponseEntity.ok(dealerService.addDealer(dto));
    }

    public void getDealer(){

    }

    public void updateDealer(){

    }

    public void deleteDealer(){

    }
}
