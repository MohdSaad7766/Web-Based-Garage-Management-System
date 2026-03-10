package com.aziz_motors.Web_Based.Garage.Management.System.service;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Dealer;
import com.aziz_motors.Web_Based.Garage.Management.System.entity.PaymentReceipt;
import com.aziz_motors.Web_Based.Garage.Management.System.enums.PaymentType;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.ResourceWithProvidedIdNotFoundException;
import com.aziz_motors.Web_Based.Garage.Management.System.repository.PaymentReceiptRepository;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.PaymentReceiptRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.PaginatedResponse;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.PaymentReceiptResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentReceiptService {

    private final PaymentReceiptRepository paymentReceiptRepository;
    private final DealerService dealerService;

    @Value("${page.size}")
    private  int PAGE_SIZE;

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


    public PaymentReceiptResponseDto getPaymentReceiptById(UUID receiptId){
        return paymentReceiptRepository.findPaymentReceiptById(receiptId).orElseThrow(()->new ResourceWithProvidedIdNotFoundException("PaymentReceipt with id-"+receiptId+" not found."));
    }

    public PaginatedResponse<PaymentReceiptResponseDto> getPaymentReceiptByDealerId(int pageNo,UUID dealerId){
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, sort);

         Page<PaymentReceiptResponseDto> page = paymentReceiptRepository.findPaymentReceiptsByDealerId(dealerId, pageable);

         return new PaginatedResponse<>(page.getContent(), pageNo, page.getTotalPages(), page.getTotalElements());
    }

    public PaginatedResponse<PaymentReceiptResponseDto> getPayments(
            int pageNo,
            String receiptNumber,
            LocalDate paymentDate,
            BigDecimal minAmount,
            BigDecimal maxAmount,
            String amountInWords,
            String payeeName,
            String payerName,
            PaymentType paymentType
    ){
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, sort);

        Page<PaymentReceiptResponseDto> page = paymentReceiptRepository.findPaymentReceipts(
                pageable,
                receiptNumber,
                paymentDate,
                minAmount,
                maxAmount,
                amountInWords,
                payeeName,
                payerName,
                paymentType);

        return new PaginatedResponse<>(page.getContent(), pageNo, page.getTotalPages(), page.getTotalElements());
    }
}
