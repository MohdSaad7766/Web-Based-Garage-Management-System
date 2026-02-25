package com.aziz_motors.Web_Based.Garage.Management.System.controller;

import com.aziz_motors.Web_Based.Garage.Management.System.repository.ProductRepository;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.ProductRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.PaginatedResponse;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.ProductResponseDto;
import com.aziz_motors.Web_Based.Garage.Management.System.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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

    @PostMapping("/add-all")
    public ResponseEntity<List<UUID>> addProducts(@RequestBody List<ProductRequestDto> dtos){
        List<UUID> uuids = new ArrayList<>();

        for(ProductRequestDto dto : dtos){
            uuids.add(productService.addProduct(dto));
        }
        return ResponseEntity.ok(uuids);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable UUID id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/get-all/{pageNo}")
    public ResponseEntity<PaginatedResponse<ProductResponseDto>> getProducts(@PathVariable int pageNo){
        return ResponseEntity.ok(productService.getProducts(pageNo));
    }

    public void updateProduct(){

    }

    public void deleteProduct(){

    }
}
