package com.aziz_motors.Web_Based.Garage.Management.System.controller;

import com.aziz_motors.Web_Based.Garage.Management.System.enums.PaymentType;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.PaymentReceiptRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.PaymentReceiptUpdateRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.GeneralMessageResponse;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.PaginatedResponse;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.PaymentReceiptResponseDto;
import com.aziz_motors.Web_Based.Garage.Management.System.service.PaymentReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/payment-receipt")
public class PaymentReceiptController {

    private final PaymentReceiptService paymentReceiptService;


    @Autowired
    public PaymentReceiptController(PaymentReceiptService paymentReceiptService){
        this.paymentReceiptService = paymentReceiptService;
    }

    @PostMapping("/add/{dealerId}")
    public ResponseEntity<GeneralMessageResponse> addPaymentReceipt(@PathVariable UUID dealerId, @RequestBody PaymentReceiptRequestDto dto){
        UUID id = paymentReceiptService.addPaymentReceipt(dealerId, dto);
        return ResponseEntity.ok(
                new GeneralMessageResponse(
                        true,
                        "Payment Receipt with id-"+id+" has been added successful."
                )
        );
    }

    @GetMapping("/{receiptId}")
    public ResponseEntity<PaymentReceiptResponseDto> getPaymentReceiptById(@PathVariable UUID receiptId){
        return ResponseEntity.ok(paymentReceiptService.getPaymentReceiptResponseById(receiptId));
    }

    @GetMapping("/get-all-by-dealer-id/{pageNo}")
    public ResponseEntity<PaginatedResponse<PaymentReceiptResponseDto>> getPaymentReceiptsByDealerId(@PathVariable int pageNo,@RequestParam UUID dealerId){
        return ResponseEntity.ok(paymentReceiptService.getPaymentReceiptByDealerId(pageNo,dealerId));
    }

    @GetMapping("/get-all/{pageNo}")
    public ResponseEntity<PaginatedResponse<PaymentReceiptResponseDto>> getPaymentReceipts(
            @PathVariable int pageNo,
            @RequestParam(required = false) String receiptNumber,
            @RequestParam(required = false) LocalDate paymentDate,
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount,
            @RequestParam(required = false) String amountInWords,
            @RequestParam(required = false) String payeeName,
            @RequestParam(required = false) String payerName,
            @RequestParam(required = false) PaymentType paymentType){
        return ResponseEntity.ok(paymentReceiptService.getPayments(
                pageNo,
                receiptNumber,
                paymentDate,
                minAmount,
                maxAmount,
                amountInWords,
                payeeName,
                payerName,
                paymentType));
    }


    @PutMapping
    public ResponseEntity<GeneralMessageResponse> updatePaymentReceipt(@RequestBody PaymentReceiptUpdateRequestDto dto){
        paymentReceiptService.updatedPaymentReceipt(dto);
        return ResponseEntity.ok(
                new GeneralMessageResponse(
                        true,
                        "Payment Receipt with id-"+dto.getId()+" has been updated successful."
                )
        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralMessageResponse> deletePaymentReceipt(@PathVariable UUID id){
        paymentReceiptService.deleteReceipt(id);
        return ResponseEntity.ok(
                new GeneralMessageResponse(
                        true,
                        "Payment Receipt with id-"+id+" has been deleted successful."
                )
        );
    }
}
