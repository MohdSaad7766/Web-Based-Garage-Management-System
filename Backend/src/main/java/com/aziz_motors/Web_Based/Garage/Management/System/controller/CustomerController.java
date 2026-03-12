package com.aziz_motors.Web_Based.Garage.Management.System.controller;

import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.CustomerRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.CustomerUpdateRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.CustomerResponseDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.FullCustomerResponseDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.GeneralMessageResponse;
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
    public ResponseEntity<GeneralMessageResponse> addCustomer(@RequestBody CustomerRequestDto dto){
        UUID customerId = customerService.addCustomer(dto);
        return ResponseEntity.ok(
                new GeneralMessageResponse(
                        true,
                        "Customer with id-"+customerId+" has been added successful."
                )
        );
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

    @PutMapping
    public ResponseEntity<GeneralMessageResponse> updateCustomer(@RequestBody CustomerUpdateRequestDto dto){
        customerService.updateCustomer(dto);
        return ResponseEntity.ok(
                new GeneralMessageResponse(
                true,
                "Customer with id-"+dto.getId()+" has been updated successful."
                )
        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralMessageResponse> deleteCustomerById(@PathVariable UUID id){
        customerService.deleteCustomer(id);
        return ResponseEntity.ok(
                new GeneralMessageResponse(
                true,
                "Customer with id-"+id+" has been deleted successful."
            )
        );
    }


}
