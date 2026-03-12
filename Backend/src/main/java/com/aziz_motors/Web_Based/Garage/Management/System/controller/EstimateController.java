package com.aziz_motors.Web_Based.Garage.Management.System.controller;

import com.aziz_motors.Web_Based.Garage.Management.System.enums.EstimateStatus;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.EstimateRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.EstimateResponseDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.GeneralMessageResponse;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.PaginatedResponse;
import com.aziz_motors.Web_Based.Garage.Management.System.service.EstimateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/estimate")
public class EstimateController {

    private final EstimateService estimateService;

    EstimateController(EstimateService estimateService){
        this.estimateService = estimateService;
    }

    @PostMapping("/add")
    public ResponseEntity<GeneralMessageResponse> addEstimate(@RequestBody EstimateRequestDto dto){
        UUID estimateId = estimateService.addEstimate(dto);
        return ResponseEntity.ok(
                new GeneralMessageResponse(
                        true,
                        "Estimate with id-"+estimateId+" has been added successful."
                )
        );
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
            @RequestParam(required = false) String vehicleRegistrationNumber,
            @RequestParam(required = false) BigDecimal minGrandTotal,
            @RequestParam(required = false) BigDecimal maxGrandTotal){
        return ResponseEntity.ok(estimateService.getEstimates(
                pageNo,
                estimateNumber,
                status,
                customerName,
                customerAddress,
                vehicleManufacturerName,
                vehicleModelName,
                vehicleRegistrationNumber,
                minGrandTotal,
                maxGrandTotal
        ));
    }

    @PatchMapping("/update-status/{estimateId}")
    public ResponseEntity<GeneralMessageResponse> updateEstimateStatus(@PathVariable UUID estimateId, @RequestParam EstimateStatus status){
        estimateService.updateEstimateStatus(estimateId, status);
        return ResponseEntity.ok(
                new GeneralMessageResponse(
                        true,
                        "Estimate with id-"+estimateId+" has been updated successful."
                )
        );
    }

    @PutMapping("/{estimateId}")
    public ResponseEntity<GeneralMessageResponse> updateEstimate(@PathVariable UUID estimateId, @RequestBody EstimateRequestDto dto){
        estimateService.updateEstimate(estimateId, dto);
        return ResponseEntity.ok(
                new GeneralMessageResponse(
                        true,
                        "Estimate with id-"+estimateId+" has been updated successful."
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralMessageResponse> deleteEstimate(@PathVariable UUID id){
        estimateService.deleteEstimateById(id);
        return ResponseEntity.ok(
                new GeneralMessageResponse(
                        true,
                        "Estimate with id-"+id+" has been deleted successful."
                )
        );
    }

    public void convertEstimateToInvoice(){

    }

    public void sendEstimateToCustomerViaEmail(){

    }

    public void generateEstimatePdf(){

    }
}
