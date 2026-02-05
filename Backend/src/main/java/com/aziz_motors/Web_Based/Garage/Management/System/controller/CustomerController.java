package com.aziz_motors.Web_Based.Garage.Management.System.controller;

import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.CustomerRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.FullCustomerResponseDto;
import com.aziz_motors.Web_Based.Garage.Management.System.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping("/add")
    public ResponseEntity<UUID> addCustomer(@RequestBody CustomerRequestDto dto){
        return ResponseEntity.ok(customerService.addCustomer(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullCustomerResponseDto> getCustomerById(@PathVariable UUID id){
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    public void getCustomers(){

    }

    public void updateCustomer(){

    }

    public void deleteCustomer(){

    }


}
