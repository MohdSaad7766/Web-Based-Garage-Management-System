package com.aziz_motors.Web_Based.Garage.Management.System.controller;

import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.PaymentReceiptRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.service.PaymentReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    public void getPaymentReceiptById(){

    }

    public void getPaymentReceiptsByDealerId(){

    }

    public void getPaymentReceipts(){

    }

    public void updatePaymentReceipt(){

    }

    public void deletePaymentReceipt(){

    }
}
