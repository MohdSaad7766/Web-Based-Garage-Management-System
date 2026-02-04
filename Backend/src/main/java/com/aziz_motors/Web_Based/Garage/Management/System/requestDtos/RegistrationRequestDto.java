package com.aziz_motors.Web_Based.Garage.Management.System.requestDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class RegistrationRequestDto {
    private String name;

    private String email;

    private String password;

    private String mobileNumber;
}
