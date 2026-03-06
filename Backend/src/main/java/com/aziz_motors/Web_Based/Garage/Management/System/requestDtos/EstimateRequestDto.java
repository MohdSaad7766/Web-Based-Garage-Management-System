package com.aziz_motors.Web_Based.Garage.Management.System.requestDtos;

import com.aziz_motors.Web_Based.Garage.Management.System.enums.EstimateStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class EstimateRequestDto {

    private String estimateNumber;

    private UUID customerId;

    private UUID vehicleId;

    private LocalDate issueDate;

    private LocalDate validUntil;

    private String notes;

    private EstimateStatus status;

    private BigDecimal grandTotal;

    private List<EstimateItemRequestDto> estimateItems = new ArrayList<>();
}
