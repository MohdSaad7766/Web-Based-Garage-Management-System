package com.aziz_motors.Web_Based.Garage.Management.System.requestDtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerRequestDto {

    private String name;

    private String email;

    private String mobileNumber;

    private String address;
}
