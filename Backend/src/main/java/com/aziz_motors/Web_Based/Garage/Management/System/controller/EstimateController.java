package com.aziz_motors.Web_Based.Garage.Management.System.controller;

import com.aziz_motors.Web_Based.Garage.Management.System.enums.EstimateStatus;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.EstimateRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.EstimateResponseDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.PaginatedResponse;
import com.aziz_motors.Web_Based.Garage.Management.System.service.EstimateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/estimate")
public class EstimateController {

    private final EstimateService estimateService;

    EstimateController(EstimateService estimateService){
        this.estimateService = estimateService;
    }

    @PostMapping("/add")
    public ResponseEntity<UUID> addEstimate(@RequestBody EstimateRequestDto dto){
        return ResponseEntity.ok(estimateService.addEstimate(dto));
    }

    @GetMapping("/{estimateId}")
    public ResponseEntity<EstimateResponseDto> getEstimateById(@PathVariable UUID estimateId){
        return ResponseEntity.ok(estimateService.getEstimateResponseById(estimateId));
    }

    @GetMapping("get-all-by-customer-id/{pageNo}")
    public ResponseEntity<PaginatedResponse<EstimateResponseDto>> getEstimatesByCustomerId(@PathVariable int pageNo, @RequestParam UUID customerId){
        return ResponseEntity.ok(estimateService.getEstimatesByCustomerId(pageNo, customerId));
    }

    @GetMapping("/get-all/{pageNo}")
    public ResponseEntity<PaginatedResponse<EstimateResponseDto>> getEstimates(
            @PathVariable() int pageNo,
            @RequestParam(required = false) String estimateNumber,
            @RequestParam(required = false) EstimateStatus status,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) String customerAddress,
            @RequestParam(required = false) String vehicleManufacturerName,
            @RequestParam(required = false) String vehicleModelName,
            @RequestParam(required = false) String vehicleRegistrationNumber){
        return ResponseEntity.ok(estimateService.getEstimates(
                pageNo,
                estimateNumber,
                status,
                customerName,
                customerAddress,
                vehicleManufacturerName,
                vehicleModelName,
                vehicleRegistrationNumber
        ));
    }

    @PatchMapping("/update-status/{estimateId}")
    public ResponseEntity<UUID> updateEstimateStatus(@PathVariable UUID estimateId, @RequestParam EstimateStatus status){
        return ResponseEntity.ok(estimateService.updateEstimateStatus(estimateId, status));
    }

    @PutMapping("/update/{estimateId}")
    public ResponseEntity<UUID> updateEstimate(@PathVariable UUID estimateId, @RequestBody EstimateRequestDto dto){
        return ResponseEntity.ok(estimateService.updateEstimate(estimateId, dto));
    }

    @DeleteMapping("/delete/{estimateId}")
    public ResponseEntity<?> deleteEstimate(@PathVariable UUID estimateId){
        estimateService.deleteEstimateById(estimateId);
        return ResponseEntity.ok("Estimate with id"+estimateId+ " has been deleted successfully.");
    }

    public void convertEstimateToInvoice(){

    }

    public void sendEstimateToCustomerViaEmail(){

    }

    public void generateEstimatePdf(){

    }
}
