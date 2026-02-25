package com.aziz_motors.Web_Based.Garage.Management.System.service;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Product;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.ProductAlreadyExistsException;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.ResourceWithProvidedIdNotFoundException;
import com.aziz_motors.Web_Based.Garage.Management.System.repository.ProductRepository;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.ProductRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.PaginatedResponse;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.ProductResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Value("${page.size}")
    private int PAGE_SIZE;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public UUID addProduct(ProductRequestDto dto){
        if(productRepository.findByNameAndManufacturer(dto.getName(), dto.getManufacturer()).isPresent()){
            throw new ProductAlreadyExistsException();
        }

        Product product = fromDto(dto);
        return productRepository.save(product).getId();
    }

    public ProductResponseDto getProductById(UUID id){
        Product product = productRepository.findById(id).orElseThrow(()->
                new ResourceWithProvidedIdNotFoundException());

        return toDto(product);
    }

    public PaginatedResponse<ProductResponseDto> getProducts(int pageNo){
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, sort);

        Page<ProductResponseDto> page = productRepository.findAllByPage(pageable);

        return new PaginatedResponse<>(
                page.getContent(),
                pageNo,
                page.getTotalPages(),
                page.getTotalElements()
        );
    }

    private ProductResponseDto toDto(Product product){
        ProductResponseDto dto = new ProductResponseDto();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setType(product.getType());
        dto.setTaxPercentage(product.getTaxPercentage());
        dto.setUnit(product.getUnit());
        dto.setActive(product.isActive());
        dto.setManufacturer(product.getManufacturer());
        dto.setHsnCode(product.getHsnCode());
        dto.setBasePrice(product.getBasePrice());
        dto.setPartNumber(product.getPartNumber());

        return dto;
    }

    private Product fromDto(ProductRequestDto dto){
        Product product = new Product();

        product.setName(dto.getName());
        product.setManufacturer(dto.getManufacturer());
        product.setActive(dto.isActive());
        product.setType(dto.getType());
        product.setUnit(dto.getUnit());
        product.setBasePrice(dto.getBasePrice());
        product.setHsnCode(dto.getHsnCode());
        product.setPartNumber(dto.getPartNumber());
        product.setTaxPercentage(dto.getTaxPercentage());

        return product;

    }
}
