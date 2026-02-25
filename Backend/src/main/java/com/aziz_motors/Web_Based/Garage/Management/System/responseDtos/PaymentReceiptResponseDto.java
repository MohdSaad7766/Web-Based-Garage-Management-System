package com.aziz_motors.Web_Based.Garage.Management.System.responseDtos;

import com.aziz_motors.Web_Based.Garage.Management.System.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PaymentReceiptResponseDto {

    private UUID id;

    private String receiptNumber;

    private LocalDate paymentDate;

    private BigDecimal amount;

    private String amountInWords;

    private String payeeName; // person who receives the money

    private String payerName;  // person who pays the money

    private PaymentType paymentType;

    private UUID dealerId;
//    private String receiptImageUrl;



}
