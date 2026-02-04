package com.aziz_motors.Web_Based.Garage.Management.System.requestDtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class LoginRequestDto {
    @NotNull
    @Email
    private String email;
    private String password;
}
