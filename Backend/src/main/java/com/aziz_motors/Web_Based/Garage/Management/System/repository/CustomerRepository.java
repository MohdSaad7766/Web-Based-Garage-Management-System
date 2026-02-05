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


    @Query("""
            Select new com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.CustomerResponseDto(
                c.id,
                c.name,
                c.email,
                c.mobileNumber,
                c.address
            )
            FROM Customer c
            """)
    Page<CustomerResponseDto> findAllByPage(Pageable pageable);
}