package com.aziz_motors.Web_Based.Garage.Management.System.responseDtos;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CustomerResponseDto {
    private UUID id;

    private String name;

    private String email;

    private String mobileNumber;

    private String address;

    public CustomerResponseDto(Customer customer){

        this.setId(customer.getId());
        this.setName(customer.getName());
        this.setEmail(customer.getEmail());
        this.setMobileNumber(customer.getMobileNumber());
        this.setAddress(customer.getAddress());

    }
}
