package com.aziz_motors.Web_Based.Garage.Management.System.repository;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Appointment;
import com.aziz_motors.Web_Based.Garage.Management.System.entity.Customer;
import com.aziz_motors.Web_Based.Garage.Management.System.entity.Vehicle;
import com.aziz_motors.Web_Based.Garage.Management.System.enums.AppointmentStatus;
import com.aziz_motors.Web_Based.Garage.Management.System.enums.ServiceType;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.FullAppointmentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    boolean existsByCustomerAndVehicleAndDateTimeAndServiceType(Customer customer,
                                                                            Vehicle vehicle,
                                                                            LocalDateTime dateTime,
                                                                            ServiceType serviceType);
    @Query(value = """
            SELECT new com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.FullAppointmentResponseDto
            (
                a.id,
                a.dateTime,
                a.serviceType,
                a.status,
                a.createdAt,
                a.updatedAt,
                null,
                null
            )
            FROM Appointment a
            WHERE 
                (cast(:dateTime as LocalDateTime) IS NULL OR a.dateTime = :dateTime)
            AND
                (:serviceType IS NULL OR a.serviceType = :serviceType)
            AND
                (:status IS NULL OR a.status = :status)
            """,
            countQuery =
                    """
                    SELECT COUNT(a)
                    FROM Appointment a
                    WHERE
                        (cast(:dateTime as LocalDateTime) IS NULL OR a.dateTime = :dateTime)
                    AND
                        (:serviceType IS NULL OR a.serviceType = :serviceType)
                    AND
                        (:status IS NULL OR a.status = :status)
            """
    )
    Page<FullAppointmentResponseDto> findByFiltering(
            Pageable pageable,
            LocalDateTime dateTime,
            ServiceType serviceType,
            AppointmentStatus status
    );
}