package com.aziz_motors.Web_Based.Garage.Management.System.requestDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class EstimateItemRequestDto {

    private String itemName;
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal discountPercentage;
    private BigDecimal taxPercentage;

}
