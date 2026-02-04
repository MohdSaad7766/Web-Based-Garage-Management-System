package com.aziz_motors.Web_Based.Garage.Management.System.entity;

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

    private String registrationNumber;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
