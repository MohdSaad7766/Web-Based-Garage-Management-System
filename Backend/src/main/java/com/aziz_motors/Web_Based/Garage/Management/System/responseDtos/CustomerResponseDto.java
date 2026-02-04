package com.aziz_motors.Web_Based.Garage.Management.System.responseDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CustomerResponseDto {

    private String name;

    private String email;

    private String mobileNumber;

    private String address;
}
