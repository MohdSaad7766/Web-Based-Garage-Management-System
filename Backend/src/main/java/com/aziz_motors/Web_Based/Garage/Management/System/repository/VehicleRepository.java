package com.aziz_motors.Web_Based.Garage.Management.System.repository;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Vehicle;
import com.aziz_motors.Web_Based.Garage.Management.System.enums.FuelType;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.VehicleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
    Optional<Vehicle> findByRegistrationNumber(String registrationNumber);

    @Query(value = """
            SELECT new com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.VehicleResponseDto
            (
                v.id,
                v.manufacturerName,
                v.modelName,
                v.modelYear,
                v.registrationNumber,
                v.fuelType,
                c.id
            )
            FROM Vehicle v
            JOIN v.customer c
            WHERE
                (:manufacturerName IS NULL OR LOWER(v.manufacturerName) LIKE CONCAT('%', LOWER(CAST(:manufacturerName AS text)), '%'))
            AND
                (:modelName IS NULL OR LOWER(v.modelName) LIKE CONCAT('%', LOWER(CAST(:modelName AS text)), '%'))
            AND
                (:modelYear IS NULL OR v.modelYear = :modelYear)
            AND
                (:registrationNumber IS NULL OR LOWER(v.registrationNumber) LIKE CONCAT('%', LOWER(CAST(:registrationNumber AS text)), '%'))
            AND
                (:fuelType IS NULL OR v.fuelType = :fuelType)
            AND
                (:customerId IS NULL OR c.id = :customerId)
            """,
    countQuery = """
            SELECT COUNT(v) 
            FROM Vehicle v
            JOIN v.customer c
            WHERE
                (:manufacturerName IS NULL OR LOWER(v.manufacturerName) LIKE CONCAT('%', LOWER(CAST(:manufacturerName AS text)), '%'))
            AND
                (:modelName IS NULL OR LOWER(v.modelName) LIKE CONCAT('%', LOWER(CAST(:modelName AS text)), '%'))
            AND
                (:modelYear IS NULL OR v.modelYear = :modelYear)
            AND
                (:registrationNumber IS NULL OR LOWER(v.registrationNumber) LIKE CONCAT('%', LOWER(CAST(:registrationNumber AS text)), '%'))
            AND
                (:fuelType IS NULL OR v.fuelType = :fuelType)
            AND
                (:customerId IS NULL OR c.id = :customerId)            
            """)
    Page<VehicleResponseDto> findVehicleByPage(
            Pageable pageable,
            String manufacturerName,
            String modelName,
            Integer modelYear,
            String registrationNumber,
            FuelType fuelType,
            UUID customerId
    );
}