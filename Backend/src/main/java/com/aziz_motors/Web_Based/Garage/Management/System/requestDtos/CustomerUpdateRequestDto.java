package com.aziz_motors.Web_Based.Garage.Management.System.requestDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


public class CustomerUpdateRequestDto {
    private UUID id;

    private String name;

    private String email;

    private String mobileNumber;

    private String address;
}
