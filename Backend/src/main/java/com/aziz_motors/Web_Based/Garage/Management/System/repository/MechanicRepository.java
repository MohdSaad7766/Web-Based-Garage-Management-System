package com.aziz_motors.Web_Based.Garage.Management.System.repository;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Mechanic;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.MechanicResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MechanicRepository extends JpaRepository<Mechanic, UUID> {

    Optional<Mechanic> findByEmail(String email);


    @Query(value = """
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
        WHERE
            (:name IS NULL OR LOWER(m.name) LIKE CONCAT('%', LOWER(CAST(:name AS text)), '%'))
        AND (:email IS NULL OR LOWER(m.email) LIKE CONCAT('%', LOWER(CAST(:email AS text)), '%'))
        AND (:mobileNumber IS NULL OR LOWER(m.mobileNumber) LIKE CONCAT('%', LOWER(CAST(:mobileNumber AS text)), '%'))
        AND (:minSalary IS NULL OR m.salary >= :minSalary)
        AND (:maxSalary IS NULL OR m.salary <= :maxSalary)
        AND (:address IS NULL OR LOWER(m.address) LIKE CONCAT('%', LOWER(CAST(:address AS text)), '%'))
        AND (cast(:joinDate as LocalDate) IS NULL OR m.joinDate = :joinDate)
        """,
            countQuery = """
        SELECT COUNT(m)
        FROM Mechanic m
        WHERE
            (:name IS NULL OR LOWER(m.name) LIKE CONCAT('%', LOWER(CAST(:name AS text)), '%'))
        AND (:email IS NULL OR LOWER(m.email) LIKE CONCAT('%', LOWER(CAST(:email AS text)), '%'))
        AND (:mobileNumber IS NULL OR LOWER(m.mobileNumber) LIKE CONCAT('%', LOWER(CAST(:mobileNumber AS text)), '%'))
        AND (:minSalary IS NULL OR m.salary >= :minSalary)
        AND (:maxSalary IS NULL OR m.salary <= :maxSalary)
        AND (:address IS NULL OR LOWER(m.address) LIKE CONCAT('%', LOWER(CAST(:address AS text)), '%'))
        AND (cast(:joinDate as LocalDate) IS NULL OR m.joinDate = :joinDate)
        """)
    Page<MechanicResponseDto> findAllByPage(
            Pageable pageable,
            String name,
            String email,
            String mobileNumber,
            Double minSalary,
            Double maxSalary,
            String address,
            LocalDate joinDate
    );
}