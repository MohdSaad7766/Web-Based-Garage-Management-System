package com.aziz_motors.Web_Based.Garage.Management.System.responseDtos;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.EstimateItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class EstimateItemResponseDto {
    private UUID id;
    private String itemName;
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal discountPercentage;
    private BigDecimal taxPercentage;
    private BigDecimal discountAmount;
    private BigDecimal taxAmount;
    private BigDecimal lineTotal;
    private UUID estimateId;

    public EstimateItemResponseDto(EstimateItem item){
        this.setId(item.getId());
        this.setItemName(item.getItemName());
        this.setUnitPrice(item.getUnitPrice());
        this.setQuantity(item.getQuantity());
        this.setDiscountPercentage(item.getDiscountPercentage());
        this.setTaxPercentage(item.getTaxPercentage());
        this.setDiscountAmount(item.getDiscountAmount());
        this.setTaxAmount(item.getTaxAmount());
        this.setLineTotal(item.getLineTotal());
        this.setEstimateId(item.getEstimate().getId());
    }
    public static List<EstimateItemResponseDto> getItemsResponse(List<EstimateItem> items){
        List<EstimateItemResponseDto> dtos = new ArrayList<>();

        for(EstimateItem item : items){
            dtos.add(new EstimateItemResponseDto(item));
        }

        return dtos;
    }
}
