package com.aziz_motors.Web_Based.Garage.Management.System.controller;

import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.CustomerRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.CustomerResponseDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.FullCustomerResponseDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.PaginatedResponse;
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
        return ResponseEntity.ok(customerService.getCustomerResponseById(id));
    }

    @GetMapping("/get-all/{pageNo}")
    public ResponseEntity<PaginatedResponse<CustomerResponseDto>> getCustomers(
            @PathVariable int pageNo,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String mobileNumber,
            @RequestParam(required = false) String address
            ){
        return ResponseEntity.ok(customerService.getCustomers(
                pageNo,
                name,
                email,
                mobileNumber,
                address
        ));
    }

    public void updateCustomer(){

    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteCustomerById(@PathVariable UUID id){
//
//        return ResponseEntity.ok("Customer with id-"+id+" has been deleted successful.");
//    }


}
