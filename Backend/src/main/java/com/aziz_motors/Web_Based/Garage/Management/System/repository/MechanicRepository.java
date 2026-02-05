package com.aziz_motors.Web_Based.Garage.Management.System.repository;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Mechanic;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.MechanicResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MechanicRepository extends JpaRepository<Mechanic, UUID> {

    Optional<Mechanic> findByEmail(String email);


    @Query("""
            SELECT new com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.MechanicResponseDto
            (
                m.id,
                m.name,
                m.email,
                m.mobileNumber,
                m.salary,
                m.address,
                m.joinDate
            )
            FROM Mechanic m            
            """)
    Page<MechanicResponseDto> findAllByPage(Pageable pageable);
}