package com.aziz_motors.Web_Based.Garage.Management.System.requestDtos;

import com.aziz_motors.Web_Based.Garage.Management.System.enums.AppointmentStatus;
import com.aziz_motors.Web_Based.Garage.Management.System.enums.ServiceType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class AppointmentRequestDto {

    private CustomerRequestDto customer;

    private LocalDateTime dateTime;

    private ServiceType serviceType;

    private VehicleRequestDto vehicle;

//    private AppointmentStatus status;

}
