package com.aziz_motors.Web_Based.Garage.Management.System.controller;

import com.aziz_motors.Web_Based.Garage.Management.System.enums.AppointmentStatus;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.AppointmentRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.FullAppointmentResponseDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.GeneralMessageResponse;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.PaginatedResponse;
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
    public ResponseEntity<FullAppointmentResponseDto> getAppointmentById(@PathVariable UUID id){
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }


    @GetMapping("/get-all/{pageNo}")
    public ResponseEntity<PaginatedResponse<FullAppointmentResponseDto>> getAppointments(@PathVariable int pageNo){
        return ResponseEntity.ok(appointmentService.getAppointments(pageNo));
    }


    @PatchMapping("/update-status/{id}")
    public ResponseEntity<GeneralMessageResponse> updateAppointmentStatus(@PathVariable UUID id, @RequestParam AppointmentStatus status, @RequestParam boolean sentMail){
        appointmentService.updateAppointmentStatus(id, status, sentMail);
        return ResponseEntity.ok(new GeneralMessageResponse(true,"Appointment status has been updated."));
    }

    public void updateAppointment(){

    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable UUID id){
        
    }
}

