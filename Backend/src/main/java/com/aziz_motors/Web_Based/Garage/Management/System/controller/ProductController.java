package com.aziz_motors.Web_Based.Garage.Management.System.controller;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Product;
import com.aziz_motors.Web_Based.Garage.Management.System.enums.ProductType;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.ProductRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.ProductUpdateRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.GeneralMessageResponse;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.PaginatedResponse;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.ProductResponseDto;
import com.aziz_motors.Web_Based.Garage.Management.System.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    public ResponseEntity<GeneralMessageResponse> addProduct(@RequestBody ProductRequestDto dto){
       UUID id = productService.addProduct(dto);
        return ResponseEntity.ok(
                new GeneralMessageResponse(
                        true,
                        "Product with id-"+id+" has been updated successful."
                )
        );
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
        return ResponseEntity.ok(productService.getProductResponseById(id));
    }

    @GetMapping("/get-all/{pageNo}")
    public ResponseEntity<PaginatedResponse<ProductResponseDto>> getProducts(
            @PathVariable int pageNo,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) ProductType type,
            @RequestParam(required = false) BigDecimal minBasePrice,
            @RequestParam(required = false) BigDecimal maxBasePrice,
            @RequestParam(required = false) BigDecimal taxPercentage,
            @RequestParam(required = false) String unit,
            @RequestParam(required = false) String manufacturer,
            @RequestParam(required = false) String hsnCode,
            @RequestParam(required = false) String partNumber,
            @RequestParam(required = false, defaultValue = "true") boolean active
    ){
        return ResponseEntity.ok(productService.getProducts(
                pageNo,
                name,
                type,
                minBasePrice,
                maxBasePrice,
                taxPercentage,
                unit,
                manufacturer,
                hsnCode,
                partNumber,
                active
        ));
    }

    @PutMapping
    public ResponseEntity<GeneralMessageResponse> updateProduct(@RequestBody ProductUpdateRequestDto dto){
        productService.updateProduct(dto);
        return ResponseEntity.ok(
                new GeneralMessageResponse(
                        true,
                        "Product with id-"+dto.getId()+" has been updated successful."
                )
        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralMessageResponse> deleteProduct(@PathVariable UUID id){
        productService.deleteProduct(id);
        return ResponseEntity.ok(
                new GeneralMessageResponse(
                        true,
                        "Product with id-"+id+" has been deleted successful."
                )
        );
    }
}
