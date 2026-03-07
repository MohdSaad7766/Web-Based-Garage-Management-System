package com.aziz_motors.Web_Based.Garage.Management.System.responseDtos;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.EstimateItem;
import com.aziz_motors.Web_Based.Garage.Management.System.enums.EstimateStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class EstimateResponseDto {
    private UUID id;
    private String estimateNumber;
    private EstimateStatus status;

    private LocalDate issueDate;
    private LocalDate validUntil;

    private String notes;
    private BigDecimal grandTotal;

    private CustomerResponseDto customer;
    private VehicleResponseDto vehicle;

    private List<EstimateItemResponseDto> estimateItems;

}
