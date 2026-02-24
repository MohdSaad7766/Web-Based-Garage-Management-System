package com.aziz_motors.Web_Based.Garage.Management.System.repository;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Product;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findByNameAndManufacturer(String name, String manufacturer);

    @Query("""
            SELECT new com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.ProductResponseDto
            (
                p.id,
                p.name,
                p.type,
                p.basePrice,
                p.taxPercentage,
                p.unit,
                p.manufacturer,
                p.hsnCode,
                p.active
            )
            FROM Product p""")
    Page<ProductResponseDto> findAllByPage(Pageable pageable);
}

