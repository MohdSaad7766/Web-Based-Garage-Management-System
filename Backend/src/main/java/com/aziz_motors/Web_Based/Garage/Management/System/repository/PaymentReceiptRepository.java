package com.aziz_motors.Web_Based.Garage.Management.System.repository;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.PaymentReceipt;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.PaymentReceiptResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PaymentReceiptRepository extends JpaRepository<PaymentReceipt, UUID> {
    @Query("""
            SELECT new com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.PaymentReceiptResponseDto
            (
                pr.id,
                pr.receiptNumber,
                pr.paymentDate,
                pr.amount,
                pr.amountInWords,
                pr.payeeName,
                pr.payerName,
                pr.paymentType,
                pr.dealer.id
            )
            FROM PaymentReceipt pr
            WHERE pr.id = :id
            """)
    public Optional<PaymentReceiptResponseDto> findPaymentReceiptById(@Param("id") UUID id);

}