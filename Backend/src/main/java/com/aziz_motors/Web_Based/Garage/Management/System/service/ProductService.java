package com.aziz_motors.Web_Based.Garage.Management.System.service;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Product;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.ProductAlreadyExistsException;
import com.aziz_motors.Web_Based.Garage.Management.System.repository.ProductRepository;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.ProductRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

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

    private Product fromDto(ProductRequestDto dto){
        Product product = new Product();

        product.setName(dto.getName());
        product.setManufacturer(dto.getManufacturer());
        product.setActive(dto.isActive());
        product.setType(dto.getType());
        product.setUnit(dto.getUnit());
        product.setBasePrice(dto.getBasePrice());
        product.setHsnCode(dto.getHsnCode());
        product.setTaxPercentage(dto.getTaxPercentage());

        return product;

    }
}
