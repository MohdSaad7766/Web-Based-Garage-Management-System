package com.aziz_motors.Web_Based.Garage.Management.System.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class PurchaseInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "dealer_id")
    private Dealer dealer;

    @OneToMany(mappedBy = "purchaseInvoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    List<PurchaseItem> purchaseItems = new ArrayList<>();
}
