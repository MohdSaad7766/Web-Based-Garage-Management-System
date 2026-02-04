package com.aziz_motors.Web_Based.Garage.Management.System.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class EstimateItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String itemName;

    // Always BigDecimal for money
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private int quantity;

    // Percentages (optional but useful)
    @Column(precision = 5, scale = 2)
    private BigDecimal discountPercentage;

    @Column(precision = 5, scale = 2)
    private BigDecimal taxPercentage;

    // Stored amounts (VERY IMPORTANT)
    @Column(precision = 10, scale = 2)
    private BigDecimal discountAmount;

    @Column(precision = 10, scale = 2)
    private BigDecimal taxAmount;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal lineTotal;

    @ManyToOne
    @JoinColumn(name = "estimate_id", nullable = false)
    private Estimate estimate;

}
