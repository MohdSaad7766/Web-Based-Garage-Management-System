package com.aziz_motors.Web_Based.Garage.Management.System.entity;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class PaymentReceipt {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "dealer_id")
    private Dealer dealer;
}
