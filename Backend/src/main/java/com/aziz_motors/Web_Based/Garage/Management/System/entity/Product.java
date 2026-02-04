package com.aziz_motors.Web_Based.Garage.Management.System.entity;

import com.aziz_motors.Web_Based.Garage.Management.System.enums.ProductType;
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
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductType type;   // SPARE_PART, SERVICE, CONSUMABLE

    @Column(precision = 10, scale = 2)
    private BigDecimal basePrice;

    @Column(precision = 5, scale = 2)
    private BigDecimal taxPercentage;

    private String unit; // pcs, liter, hour

    private String manufacturer;

    private String hsnCode;

    private boolean active = true;
}
