package com.aziz_motors.Web_Based.Garage.Management.System.repository;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.PaymentReceipt;
import com.aziz_motors.Web_Based.Garage.Management.System.enums.PaymentType;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.PaymentReceiptResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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


    @Query(value = """
            Select new com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.PaymentReceiptResponseDto
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
            WHERE pr.dealer.id = :dealerId
            """,
            countQuery = """
            SELECT COUNT(pr)
            FROM PaymentReceipt pr
            WHERE pr.dealer.id = :dealerId
            """)
    public Page<PaymentReceiptResponseDto> findPaymentReceiptsByDealerId(@Param("dealerId") UUID dealerId, Pageable pageable);

    @Query(value = """
            Select new com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.PaymentReceiptResponseDto
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
                WHERE
                                    (:receiptNumber IS NULL OR LOWER(pr.receiptNumber) LIKE CONCAT('%', LOWER(CAST(:receiptNumber AS text)), '%'))
                                AND
                                    (cast(:paymentDate as LocalDate) IS NULL OR pr.paymentDate = :paymentDate)
                                AND
                                    (:minAmount IS NULL OR pr.amount >= :minAmount)
                                AND
                                    (:maxAmount IS NULL OR pr.amount <= :maxAmount)
                                AND
                                    (:amountInWords IS NULL OR LOWER(pr.amountInWords) LIKE CONCAT('%', LOWER(CAST(:amountInWords AS text)), '%'))
                                AND
                                    (:payeeName IS NULL OR LOWER(pr.payeeName) LIKE CONCAT('%', LOWER(CAST(:payeeName AS text)), '%'))
                                AND
                                    (:payerName IS NULL OR LOWER(pr.payerName) LIKE CONCAT('%', LOWER(CAST(:payerName AS text)), '%'))
                                AND
                                    (:paymentType IS NULL OR pr.paymentType = :paymentType)
           
            
            """,
            countQuery = """
            SELECT COUNT(pr)
            FROM PaymentReceipt pr
                     WHERE
                                    (:receiptNumber IS NULL OR LOWER(pr.receiptNumber) LIKE CONCAT('%', LOWER(CAST(:receiptNumber AS text)), '%'))
                                AND
                                    (cast(:paymentDate as LocalDate) IS NULL OR pr.paymentDate = :paymentDate)
                                AND
                                    (:minAmount IS NULL OR pr.amount >= :minAmount)
                                AND
                                    (:maxAmount IS NULL OR pr.amount <= :maxAmount)
                                AND
                                    (:amountInWords IS NULL OR LOWER(pr.amountInWords) LIKE CONCAT('%', LOWER(CAST(:amountInWords AS text)), '%'))
                                AND
                                    (:payeeName IS NULL OR LOWER(pr.payeeName) LIKE CONCAT('%', LOWER(CAST(:payeeName AS text)), '%'))
                                AND
                                    (:payerName IS NULL OR LOWER(pr.payerName) LIKE CONCAT('%', LOWER(CAST(:payerName AS text)), '%'))
                                AND
                                    (:paymentType IS NULL OR pr.paymentType = :paymentType)
            
            
            """)
    public Page<PaymentReceiptResponseDto> findPaymentReceipts(
            Pageable pageable,
            String receiptNumber,
            LocalDate paymentDate,
            BigDecimal minAmount,
            BigDecimal maxAmount,
            String amountInWords,
            String payeeName,
            String payerName,
            PaymentType paymentType
    );

    /* WHERE
                (:receiptNumber IS NULL OR LOWER(pr.receiptNumber) LIKE CONCAT('%', LOWER(CAST(:receiptNumber AS text)), '%'))
            AND
                (cast(:paymentDate as LocalDate) IS NULL OR pr.paymentDate = :paymentDate)
            AND
                (:minAmount IS NULL OR pr.amount >= :minAmount)
            AND
                (:maxAmount IS NULL OR pr.amount <= :maxAmount)
            AND
                (:amountInWords IS NULL OR LOWER(pr.amountInWords) LIKE CONCAT('%', LOWER(CAST(:amountInWords AS text))
            AND
                (:payeeName IS NULL OR LOWER(pr.payeeName) LIKE CONCAT('%', LOWER(CAST(:payeeName AS text))
            AND
                (:payerName IS NULL OR LOWER(pr.payerName) LIKE CONCAT('%', LOWER(CAST(:payerName AS text))
            AND
                (:paymentType IS NULL OR pr.paymentType = :paymentType)*/
}