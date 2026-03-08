package com.aziz_motors.Web_Based.Garage.Management.System.repository;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Estimate;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.EstimateResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EstimateRepository extends JpaRepository<Estimate, UUID> {

    @Query(value = """
        SELECT new com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.EstimateResponseDto
        (
            e.id,
            e.estimateNumber,
            e.status,
            e.issueDate,
            e.validUntil,
            e.notes,
            e.grandTotal,
            new com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.CustomerResponseDto
            (
                c.id,
                c.name,
                c.email,
                c.mobileNumber,
                c.address
            ),
            new com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.VehicleResponseDto
            (
                v.id,
                v.manufacturerName,
                v.modelName,
                v.modelYear,
                v.registrationNumber,
                v.fuelType
            ),
            null
        )
        FROM Estimate e
        JOIN e.customer c
        JOIN e.vehicle v
        WHERE c.id = :customerId
        """,
            countQuery = """
        SELECT COUNT(e)
        FROM Estimate e
        WHERE e.customer.id = :customerId
        """
    )
    Page<EstimateResponseDto> findEstimatesByCustomerId(
            @Param("customerId") UUID customerId,
            Pageable pageable
    );

    @Query(value = """
        SELECT new com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.EstimateResponseDto
        (
            e.id,
            e.estimateNumber,
            e.status,
            e.issueDate,
            e.validUntil,
            e.notes,
            e.grandTotal,
            new com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.CustomerResponseDto
            (
                c.id,
                c.name,
                c.email,
                c.mobileNumber,
                c.address
            ),
            new com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.VehicleResponseDto
            (
                v.id,
                v.manufacturerName,
                v.modelName,
                v.modelYear,
                v.registrationNumber,
                v.fuelType
            ),
            null
        )
        FROM Estimate e
        JOIN e.customer c
        JOIN e.vehicle v
        """,
            countQuery = """
        SELECT COUNT(e)
        FROM Estimate e
        """
    )
    Page<EstimateResponseDto> findEstimates(
            Pageable pageable
    );
}