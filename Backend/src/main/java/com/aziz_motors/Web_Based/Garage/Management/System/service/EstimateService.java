package com.aziz_motors.Web_Based.Garage.Management.System.service;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Customer;
import com.aziz_motors.Web_Based.Garage.Management.System.entity.Estimate;
import com.aziz_motors.Web_Based.Garage.Management.System.entity.EstimateItem;
import com.aziz_motors.Web_Based.Garage.Management.System.repository.EstimateItemRepository;
import com.aziz_motors.Web_Based.Garage.Management.System.repository.EstimateRepository;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.EstimateItemRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.EstimateRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EstimateService {
    private final EstimateItemRepository estimateItemRepository;
    private final EstimateRepository  estimateRepository;
    private final CustomerService customerService;

    public EstimateService(EstimateItemRepository estimateItemRepository, CustomerService customerService, EstimateRepository  estimateRepository){
        this.estimateItemRepository = estimateItemRepository;
        this.customerService = customerService;
        this.estimateRepository = estimateRepository;
    }

    @Transactional
    public UUID addEstimate(EstimateRequestDto dto){
        Customer customer = customerService.getCustomerById(dto.getCustomerId());

        Estimate estimate = fromDto(dto);
        estimate.setCustomer(customer);
        customer.getEstimates().add(estimate);

        return estimateRepository.save(estimate).getId();
    }

    private Estimate fromDto(EstimateRequestDto dto){
        Estimate estimate = new Estimate();

        List<EstimateItem> items = fromDto(dto.getEstimateItems());

        for(EstimateItem item : items){
            item.setEstimate(estimate);   
        }

        estimate.setEstimateItems(items);

        return estimate;
    }

    private List<EstimateItem> fromDto(List<EstimateItemRequestDto> dtos){
        List<EstimateItem> items = new ArrayList<>();

        for(EstimateItemRequestDto dto : dtos){
            EstimateItem estimateItem = fromDto(dto);
            items.add(estimateItem);
        }

        return items;
    }

    private EstimateItem fromDto(EstimateItemRequestDto dto){
        EstimateItem item = new EstimateItem();

        item.setItemName(dto.getItemName());
        item.setUnitPrice(dto.getUnitPrice());
        item.setQuantity(dto.getQuantity());
        item.setDiscountPercentage(dto.getDiscountPercentage());
        item.setTaxPercentage(dto.getTaxPercentage());
        item.setDiscountAmount(dto.getDiscountAmount());
        item.setTaxAmount(dto.getTaxAmount());
        item.setLineTotal(dto.getLineTotal());

        return item;
    }
}
