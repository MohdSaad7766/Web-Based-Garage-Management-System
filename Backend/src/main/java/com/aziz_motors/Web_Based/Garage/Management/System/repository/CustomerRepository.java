package com.aziz_motors.Web_Based.Garage.Management.System.repository;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Customer;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.CustomerResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findByEmail(String email);


    @Query(value = """
            Select new com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.CustomerResponseDto(
                c.id,
                c.name,
                c.email,
                c.mobileNumber,
                c.address
            )
            FROM Customer c
            
            WHERE
                (:name IS NULL OR LOWER(c.name) LIKE CONCAT('%', LOWER( CAST(:name AS text)), '%'))
            AND
                (:email IS NULL OR LOWER(c.email) LIKE CONCAT('%', LOWER( CAST(:email AS text)), '%'))
            AND
                (:mobileNumber IS NULL OR LOWER(c.mobileNumber) LIKE CONCAT('%', LOWER( CAST(:mobileNumber AS text)), '%'))
            AND
                (:address IS NULL OR LOWER(c.address) LIKE CONCAT('%', LOWER( CAST(:address AS text)), '%'))
            """
    ,countQuery = """
            SELECT COUNT(c) 
            FROM Customer c
             WHERE
                (:name IS NULL OR LOWER(c.name) LIKE CONCAT('%', LOWER( CAST(:name AS text)), '%'))
            AND
                (:email IS NULL OR LOWER(c.email) LIKE CONCAT('%', LOWER( CAST(:email AS text)), '%'))
            AND
                (:mobileNumber IS NULL OR LOWER(c.mobileNumber) LIKE CONCAT('%', LOWER( CAST(:mobileNumber AS text)), '%'))
            AND
                (:address IS NULL OR LOWER(c.address) LIKE CONCAT('%', LOWER( CAST(:address AS text)), '%'))
            """)
    Page<CustomerResponseDto> findAllByPage(
            Pageable pageable,
            String name,
            String email,
            String mobileNumber,
            String address
    );
}