package com.aziz_motors.Web_Based.Garage.Management.System.controller;

import com.aziz_motors.Web_Based.Garage.Management.System.repository.ProductRepository;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.ProductRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.ProductResponseDto;
import com.aziz_motors.Web_Based.Garage.Management.System.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<UUID> addProduct(@RequestBody ProductRequestDto dto){
        return ResponseEntity.ok(productService.addProduct(dto));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable UUID id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    public void getProducts(){

    }

    public void updateProduct(){

    }

    public void deleteProduct(){

    }
}
