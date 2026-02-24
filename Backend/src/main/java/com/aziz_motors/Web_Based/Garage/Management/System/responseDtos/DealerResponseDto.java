package com.aziz_motors.Web_Based.Garage.Management.System.responseDtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class DealerResponseDto {

    private UUID id;

    private String name;

    private String gstNumber;
    private String panNumber;

    private String address;
    private String city;
    private String state;
    private String zipcode;
    private String country;

    private String contactPersonName;
    private String contactPersonPhone;
    private String contactPersonEmail;

    private Set<String> phoneNumbers = new HashSet<>();

    private String email;
    private String website;

    private String bankName;
    private String bankAccountNumber;
    private String bankIFSC;



//    @OneToMany(mappedBy = "dealer", cascade = CascadeType.ALL)
//    private List<PurchaseInvoice> purchaseInvoices = new ArrayList<>();
//
//    @OneToMany(mappedBy = "dealer", cascade = CascadeType.ALL)
//    private List<PaymentReceipt> paymentReceipts = new ArrayList<>();
}
