package com.aziz_motors.Web_Based.Garage.Management.System.entity;


import com.aziz_motors.Web_Based.Garage.Management.System.enums.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unq_dealer_receipt_no",
                        columnNames = {"receiptNumber","dealer_id"}
                )
        }
)
public class PaymentReceipt {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String receiptNumber;

    @Column(nullable = false)
    private LocalDate paymentDate;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String amountInWords;

    @Column(nullable = false)
    private String payeeName; // person who receives the money

    @Column(nullable = false)
    private String payerName;  // person who pays the money

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private PaymentType paymentType;

//    private String receiptImageUrl;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "dealer_id", nullable = false)
    private Dealer dealer;
}
