package com.aziz_motors.Web_Based.Garage.Management.System.responseDtos;

import com.aziz_motors.Web_Based.Garage.Management.System.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductResponseDto {

    private UUID id;

    private String name;

    private ProductType type;   // SPARE_PART, SERVICE, CONSUMABLE

    private BigDecimal basePrice;

    private BigDecimal taxPercentage;

    private String unit; // pcs, liter, hour

    private String manufacturer;

    private String hsnCode;

    private boolean active = true;
}
