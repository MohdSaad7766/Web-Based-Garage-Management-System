package com.aziz_motors.Web_Based.Garage.Management.System.controller;

import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.DealerRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.DealerUpdateRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.DealerResponseDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.GeneralMessageResponse;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.PaginatedResponse;
import com.aziz_motors.Web_Based.Garage.Management.System.service.DealerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/dealer")
public class DealerController {

    private final DealerService dealerService;

    public DealerController(DealerService dealerService){
        this.dealerService = dealerService;
    }

    @PostMapping("/add")
    public ResponseEntity<GeneralMessageResponse> addDealer(@RequestBody DealerRequestDto dto){
        UUID id = dealerService.addDealer(dto);
        return ResponseEntity.ok(
                new GeneralMessageResponse(
                        true,
                        "Dealer with id-"+id+" has been added successful."
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<DealerResponseDto> getDealerById(@PathVariable UUID id){
        return ResponseEntity.ok(dealerService.getDealerResponseById(id));
    }

    @GetMapping("/get-all/{pageNo}")
    public ResponseEntity<PaginatedResponse<DealerResponseDto>> getDealers(
            @PathVariable int pageNo,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String gstNumber,
            @RequestParam(required = false) String panNumber,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String zipcode,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String contactPersonName,
            @RequestParam(required = false) String contactPersonPhone,
            @RequestParam(required = false) String contactPersonEmail,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String website,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String bankName,
            @RequestParam(required = false) String bankAccountNumber,
            @RequestParam(required = false) String bankIFSC
    ){
        return ResponseEntity.ok(dealerService.getDealers(
                pageNo,
                name,
                gstNumber,
                panNumber,
                address,
                city,
                state,
                zipcode,
                country,
                contactPersonName,
                contactPersonPhone,
                contactPersonEmail,
                phoneNumber,
                website,
                email,
                bankName,
                bankAccountNumber,
                bankIFSC
        ));
    }

    @PutMapping
    public ResponseEntity<GeneralMessageResponse> updateDealer(@RequestBody DealerUpdateRequestDto dto){
        dealerService.updateDealer(dto);
        return ResponseEntity.ok(
                new GeneralMessageResponse(
                        true,
                        "Dealer with id-"+dto.getId()+" has been updated successful."
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralMessageResponse> deleteDealer(@PathVariable UUID id){
        dealerService.deleteDealer(id);
        return ResponseEntity.ok(
                new GeneralMessageResponse(
                        true,
                        "Dealer with id-"+id+" has been deleted successful."
                )
        );
    }
}
