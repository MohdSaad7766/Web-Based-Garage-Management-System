package com.aziz_motors.Web_Based.Garage.Management.System.service;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Dealer;
import com.aziz_motors.Web_Based.Garage.Management.System.entity.PaymentReceipt;
import com.aziz_motors.Web_Based.Garage.Management.System.repository.PaymentReceiptRepository;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.PaymentReceiptRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PaymentReceiptService {

    private final PaymentReceiptRepository paymentReceiptRepository;
    private final DealerService dealerService;

    @Autowired
    public PaymentReceiptService(
            PaymentReceiptRepository paymentReceiptRepository,
            DealerService dealerService
    ){
        this.paymentReceiptRepository = paymentReceiptRepository;
        this.dealerService = dealerService;
    }

    @Transactional
    public UUID addPaymentReceipt(UUID dealerId, PaymentReceiptRequestDto dto){
        Dealer dealer = dealerService.getDealerById(dealerId);

        PaymentReceipt paymentReceipt = fromDto(dto);
        dealer.getPaymentReceipts().add(paymentReceipt);
        paymentReceipt.setDealer(dealer);

        return paymentReceiptRepository.save(paymentReceipt).getId();
    }

    private PaymentReceipt fromDto(PaymentReceiptRequestDto dto){
        PaymentReceipt paymentReceipt = new PaymentReceipt();

        paymentReceipt.setReceiptNumber(dto.getReceiptNumber());
        paymentReceipt.setPaymentType(dto.getPaymentType());
        paymentReceipt.setPaymentDate(dto.getPaymentDate());
        paymentReceipt.setAmount(dto.getAmount());
        paymentReceipt.setAmountInWords(dto.getAmountInWords());
        paymentReceipt.setPayeeName(dto.getPayeeName());
        paymentReceipt.setPayerName(dto.getPayerName());

        return paymentReceipt;
    }
}
