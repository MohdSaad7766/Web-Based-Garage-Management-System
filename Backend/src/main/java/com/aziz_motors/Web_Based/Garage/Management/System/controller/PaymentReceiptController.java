package com.aziz_motors.Web_Based.Garage.Management.System.controller;

import com.aziz_motors.Web_Based.Garage.Management.System.enums.PaymentType;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.PaymentReceiptRequestDto;
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
    public ResponseEntity<UUID> addPaymentReceipt(@PathVariable UUID dealerId, @RequestBody PaymentReceiptRequestDto dto){
        return ResponseEntity.ok(paymentReceiptService.addPaymentReceipt(dealerId, dto));
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

    public void updatePaymentReceipt(){

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePaymentReceipt(@PathVariable UUID id){
        paymentReceiptService.deleteReceipt(id);
        return ResponseEntity.ok("Payment Receipt with id"+id+ " has been deleted successful.");
    }
}
