package com.aziz_motors.Web_Based.Garage.Management.System.requestDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class DealerRequestDto {

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


    private String phoneNumber;
    private String email;
    private String website;

    private String bankName;
    private String bankAccountNumber;
    private String bankIFSC;


    List<PaymentReceiptRequestDto> paymentReceipts = new ArrayList<>();

}
