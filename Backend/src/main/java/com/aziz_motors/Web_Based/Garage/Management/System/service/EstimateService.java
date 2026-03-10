package com.aziz_motors.Web_Based.Garage.Management.System.service;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Customer;
import com.aziz_motors.Web_Based.Garage.Management.System.entity.Estimate;
import com.aziz_motors.Web_Based.Garage.Management.System.entity.EstimateItem;
import com.aziz_motors.Web_Based.Garage.Management.System.entity.Vehicle;
import com.aziz_motors.Web_Based.Garage.Management.System.enums.EstimateStatus;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.ResourceWithProvidedIdNotFoundException;
import com.aziz_motors.Web_Based.Garage.Management.System.repository.EstimateItemRepository;
import com.aziz_motors.Web_Based.Garage.Management.System.repository.EstimateRepository;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.EstimateItemRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.EstimateRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EstimateService {
    private final EstimateItemRepository estimateItemRepository;
    private final EstimateRepository  estimateRepository;
    private final CustomerService customerService;
    private final VehicleService vehicleService;

    @Value("${page.size}")
    private int PAGE_SIZE;

    public EstimateService(EstimateItemRepository estimateItemRepository,
                           CustomerService customerService,
                           EstimateRepository  estimateRepository,
                           VehicleService vehicleService){
        this.estimateItemRepository = estimateItemRepository;
        this.customerService = customerService;
        this.estimateRepository = estimateRepository;
        this.vehicleService = vehicleService;
    }

    @Transactional
    public UUID addEstimate(EstimateRequestDto dto){
        Customer customer = customerService.getCustomerById(dto.getCustomerId());
        Vehicle vehicle = vehicleService.getVehicleById(dto.getVehicleId());

        Estimate estimate = fromDto(dto);
        estimate.setCustomer(customer);
        estimate.setVehicle(vehicle);

        customer.getEstimates().add(estimate);

        return estimateRepository.save(estimate).getId();
    }


    public EstimateResponseDto getEstimateResponseById(UUID estimateId){
        Estimate estimate = getEstimateById(estimateId);

        EstimateResponseDto dto = new EstimateResponseDto();
        dto.setId(estimateId);
        dto.setEstimateNumber(estimate.getEstimateNumber());
        dto.setNotes(estimate.getNotes());
        dto.setStatus(estimate.getStatus());
        dto.setGrandTotal(estimate.getGrandTotal());
        dto.setIssueDate(estimate.getIssueDate());
        dto.setValidUntil(estimate.getValidUntil());

        dto.setCustomer(new CustomerResponseDto(estimate.getCustomer()));
        dto.setVehicle(new VehicleResponseDto((estimate.getVehicle())));
        dto.setEstimateItems(EstimateItemResponseDto.getItemsResponse(estimate.getEstimateItems()));

        return dto;
    }

    public Estimate getEstimateById(UUID estimateId){
        return estimateRepository.findById(estimateId).orElseThrow(()->
                new ResourceWithProvidedIdNotFoundException("Estimate with id-"+estimateId+" not found."));
    }

    public PaginatedResponse<EstimateResponseDto> getEstimatesByCustomerId(int pageNo, UUID customerId){
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, sort);
        Page<EstimateResponseDto> page = estimateRepository.findEstimatesByCustomerId(customerId, pageable);
        return new PaginatedResponse<>(
                        page.getContent(),
                        pageNo,
                        page.getTotalPages(),
                        page.getTotalElements()
                );
    }

    public PaginatedResponse<EstimateResponseDto> getEstimates(int pageNo,
                                                               String estimateNumber,
                                                               EstimateStatus status,
                                                               String customerName,
                                                               String customerAddress,
                                                               String vehicleManufacturerName,
                                                               String vehicleModelName,
                                                               String vehicleRegistrationNumber,
                                                               BigDecimal minGrandTotal,
                                                               BigDecimal maxGrandTotal
    ){
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, sort);

        Page<EstimateResponseDto> page = estimateRepository.findEstimates(
                pageable,
                estimateNumber,
                status,
                customerName,
                customerAddress,
                vehicleManufacturerName,
                vehicleModelName,
                vehicleRegistrationNumber,
                minGrandTotal,
                maxGrandTotal);

        return new PaginatedResponse<>(
                page.getContent(),
                pageNo,
                page.getTotalPages(),
                page.getTotalElements()
        );
    }

    @Transactional
    public UUID updateEstimateStatus(UUID estimateId, EstimateStatus status){
        Estimate estimate = getEstimateById(estimateId);
        estimate.setStatus(status);

        return estimateRepository.save(estimate).getId();
    }

    @Transactional
    public UUID updateEstimate(UUID estimateId, EstimateRequestDto dto){
        Estimate estimate = getEstimateById(estimateId);
        fromDto(dto, estimate);

        return estimateRepository.save(estimate).getId();
    }

    @Transactional
    public void deleteEstimateById(UUID estimateId){
        Estimate estimate = getEstimateById(estimateId);
        if(estimate.getStatus() != EstimateStatus.DRAFT){
            throw new RuntimeException("Estimate status is "+estimate.getStatus()+", it can be only updated when the Estimate DRAFT");
        }
        estimateRepository.delete(estimate);
    }

    private void fromDto(EstimateRequestDto dto, Estimate estimate){

        List<EstimateItem> newItems = fromDto(dto.getEstimateItems());

        // get existing collection
        List<EstimateItem> existingItems = estimate.getEstimateItems();

        // remove old items
        existingItems.clear();

        // add new items
        for(EstimateItem item : newItems){
            item.setEstimate(estimate);
            existingItems.add(item);
        }

        estimate.setNotes(dto.getNotes());
        estimate.setIssueDate(dto.getIssueDate());
        estimate.setValidUntil(dto.getValidUntil());

        estimate.setGrandTotal(calculateGrandTotal(existingItems));
    }



    private Estimate fromDto(EstimateRequestDto dto){
        Estimate estimate = new Estimate();

        List<EstimateItem> items = fromDto(dto.getEstimateItems());


        for(EstimateItem item : items){
            item.setEstimate(estimate);
        }

        estimate.setEstimateItems(items);
        estimate.setEstimateNumber(generateEstimateNumber());
        estimate.setNotes(dto.getNotes());
        estimate.setStatus(EstimateStatus.DRAFT);
        estimate.setIssueDate(dto.getIssueDate());
        estimate.setValidUntil(dto.getValidUntil());


        estimate.setGrandTotal(calculateGrandTotal(items));

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

    private EstimateItem fromDto(EstimateItemRequestDto dto) {
        EstimateItem item = new EstimateItem();

        item.setItemName(dto.getItemName());
        item.setUnitPrice(dto.getUnitPrice());
        item.setQuantity(dto.getQuantity());

        BigDecimal discountPercentage =
                dto.getDiscountPercentage() != null ? dto.getDiscountPercentage() : BigDecimal.ZERO;

        BigDecimal taxPercentage =
                dto.getTaxPercentage() != null ? dto.getTaxPercentage() : BigDecimal.ZERO;

        item.setDiscountPercentage(discountPercentage);
        item.setTaxPercentage(taxPercentage);

    /*
        subtotal = unitPrice × quantity
        discountAmount = subtotal × discountPercentage / 100
        taxableAmount = subtotal − discountAmount
        taxAmount = taxableAmount × taxPercentage / 100
        lineTotal = taxableAmount + taxAmount
    */

        BigDecimal quantity = BigDecimal.valueOf(dto.getQuantity());

        BigDecimal subtotal = dto.getUnitPrice().multiply(quantity);

        BigDecimal discountAmount = subtotal
                .multiply(discountPercentage)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        BigDecimal taxableAmount = subtotal.subtract(discountAmount);

        BigDecimal taxAmount = taxableAmount
                .multiply(taxPercentage)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        BigDecimal lineTotal = taxableAmount.add(taxAmount);

        item.setDiscountAmount(discountAmount);
        item.setTaxAmount(taxAmount);
        item.setLineTotal(lineTotal);

        return item;
    }

    private BigDecimal calculateGrandTotal(List<EstimateItem> items){
        BigDecimal total = BigDecimal.ZERO;

        /*

            lineTotal = (unitPrice * quantity) - discount + tax
            grandTotal = sum(lineTotal)

        */

        for(EstimateItem item : items){
            total = total.add(item.getLineTotal());
        }

        return total;
    }

    private String generateEstimateNumber() {

        long count = estimateRepository.count() + 1;

        return "EST-" + java.time.Year.now().getValue() + "-" +
                String.format("%05d", count);
    }
}
