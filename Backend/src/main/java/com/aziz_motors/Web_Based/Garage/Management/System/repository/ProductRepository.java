package com.aziz_motors.Web_Based.Garage.Management.System.repository;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Product;
import com.aziz_motors.Web_Based.Garage.Management.System.enums.ProductType;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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
                p.partNumber,
                p.active
            )
            FROM Product p
            WHERE
                ( :name IS NULL OR LOWER(p.name) LIKE CONCAT('%', LOWER(CAST( :name AS text )), '%') )
            AND
                ( :type IS NULL OR p.type = :type )
            AND
                ( :basePrice IS NULL OR p.basePrice = :basePrice )
            AND
                ( :taxPercentage IS NULL OR p.taxPercentage = :taxPercentage )
            AND
                ( :unit IS NULL OR LOWER(p.unit) LIKE CONCAT('%', LOWER(CAST( :unit AS text )), '%') )
            AND
                ( :manufacturer IS NULL OR LOWER(p.manufacturer) LIKE CONCAT('%', LOWER(CAST( :manufacturer AS text )), '%') )
            AND
                ( :hsnCode IS NULL OR LOWER(p.hsnCode) LIKE CONCAT('%', LOWER(CAST( :hsnCode AS text )), '%') )
            AND
                ( :partNumber IS NULL OR LOWER(p.partNumber) LIKE CONCAT('%', LOWER(CAST( :partNumber AS text )), '%') )
            AND
                ( :active IS NULL OR p.active = :active )
            """)
    Page<ProductResponseDto> findAllByPage(
            Pageable pageable,
            String name,
            ProductType type,
            BigDecimal basePrice,
            BigDecimal taxPercentage,
            String unit,
            String manufacturer,
            String hsnCode,
            String partNumber,
            boolean active

            );
}

