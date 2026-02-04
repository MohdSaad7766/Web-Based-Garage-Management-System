package com.aziz_motors.Web_Based.Garage.Management.System.controller;

import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.AppointmentRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.AppointmentResponseDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.GeneralMessageResponse;
import com.aziz_motors.Web_Based.Garage.Management.System.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDto> getAppointmentById(@PathVariable UUID id){
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }


//    public void getAppointments(){
//
//    }

    public void updateAppointment(){

    }

    public void deleteAppointment(){

    }
}

