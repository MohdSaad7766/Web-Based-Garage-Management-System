package com.aziz_motors.Web_Based.Garage.Management.System.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor


@Entity
public class Dealer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

//    Business Details
    private String gstNumber;
    private String panNumber;

    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String country;

//    Contact Details
    private String contactPersonName;
    private String contactPersonPhone;
    private String contactPersonEmail;

    @ElementCollection
    private List<String> phoneNumbers = new ArrayList<>();

    private String email;
    private String website;


//    Bank Details
    private String bankName;
    private String bankAccountNumber;
    private String bankIFSC;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "dealer", cascade = CascadeType.ALL)
    private List<PurchaseInvoice> purchaseInvoices = new ArrayList<>();

    @OneToMany(mappedBy = "dealer", cascade = CascadeType.ALL)
    private List<PaymentReceipt> paymentReceipts = new ArrayList<>();

}
