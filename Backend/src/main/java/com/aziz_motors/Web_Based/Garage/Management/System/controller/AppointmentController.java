package com.aziz_motors.Web_Based.Garage.Management.System.controller;

import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.AppointmentRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.GeneralMessageResponse;
import com.aziz_motors.Web_Based.Garage.Management.System.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private AppointmentService appointmentService;


    @PostMapping("/add")
    public ResponseEntity<GeneralMessageResponse> addAppointment(@RequestBody AppointmentRequestDto dto){
        appointmentService.addAppointment(dto);

        return ResponseEntity.ok(new GeneralMessageResponse(
                true,
                "Appointment Request has been raised successfully"));
    }

    public void getAppointment(){

    }

    public void updateAppointment(){

    }

    public void deleteAppointment(){

    }
}

