package com.aziz_motors.Web_Based.Garage.Management.System.entity;

import com.aziz_motors.Web_Based.Garage.Management.System.enums.FuelType;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.VehicleRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String manufacturerName;

    @Column(nullable = false)
    private String modelName;

    @Column(nullable = false)
    private int modelYear;

    @Column(nullable = false, unique = true)
    private String registrationNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FuelType fuelType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
