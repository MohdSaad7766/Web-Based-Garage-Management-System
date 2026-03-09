package com.aziz_motors.Web_Based.Garage.Management.System.repository;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Dealer;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.DealerResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface DealerRepository extends JpaRepository<Dealer, UUID> {

  Optional<Dealer> findByNameOrEmailOrGstNumber(String name, String email, String gstNumber);


  @Query(
          value = """
          SELECT new com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.DealerResponseDto
          (
            d.id,
            d.name,
            d.gstNumber,
            d.panNumber,
            d.address,
            d.city,
            d.state,
            d.zipcode,
            d.country,
            d.contactPersonName,
            d.contactPersonPhone,
            d.contactPersonEmail,
            d.phoneNumber,
            d.email,
            d.website,
            d.bankName,
            d.bankAccountNumber,
            d.bankIFSC,
            null
          )
          FROM Dealer d
          WHERE
            (:name IS NULL OR LOWER(d.name) LIKE CONCAT('%', LOWER(CAST(:name AS text)), '%'))
          AND
            (:gstNumber IS NULL OR LOWER(d.gstNumber) LIKE CONCAT('%', LOWER(CAST(:gstNumber AS text)), '%'))
          AND
            (:panNumber IS NULL OR LOWER(d.panNumber) LIKE CONCAT('%', LOWER(CAST(:panNumber AS text)), '%'))
          
          AND
            (:address IS NULL OR LOWER(d.address) LIKE CONCAT('%', LOWER(CAST(:address AS text)), '%'))
          AND
            (:city IS NULL OR LOWER(d.city) LIKE CONCAT('%', LOWER(CAST(:city AS text)), '%'))
          AND
            (:state IS NULL OR LOWER(d.state) LIKE CONCAT('%', LOWER(CAST(:state AS text)), '%'))
          AND
            (:zipcode IS NULL OR LOWER(d.zipcode) LIKE CONCAT('%', LOWER(CAST(:zipcode AS text)), '%'))
          AND
            (:country IS NULL OR LOWER(d.country) LIKE CONCAT('%', LOWER(CAST(:country AS text)), '%'))
          
          
          AND
            (:contactPersonName IS NULL OR LOWER(d.contactPersonName) LIKE CONCAT('%', LOWER(CAST(:contactPersonName AS text)), '%'))
          AND
            (:contactPersonPhone IS NULL OR LOWER(d.contactPersonPhone) LIKE CONCAT('%', LOWER(CAST(:contactPersonPhone AS text)), '%'))
          AND
            (:contactPersonEmail IS NULL OR LOWER(d.contactPersonEmail) LIKE CONCAT('%', LOWER(CAST(:contactPersonEmail AS text)), '%'))
          
          
      
          AND
            (:phoneNumber IS NULL OR LOWER(d.phoneNumber) LIKE CONCAT('%', LOWER(CAST(:phoneNumber AS text)), '%'))
          AND
            (:website IS NULL OR LOWER(d.website) LIKE CONCAT('%', LOWER(CAST(:website AS text)), '%'))
          AND
            (:email IS NULL OR LOWER(d.email) LIKE CONCAT('%', LOWER(CAST(:email AS text)), '%'))
          AND
            (:bankName IS NULL OR LOWER(d.bankName) LIKE CONCAT('%', LOWER(CAST(:bankName AS text)), '%'))
          AND
            (:bankAccountNumber IS NULL OR LOWER(d.bankAccountNumber) LIKE CONCAT('%', LOWER(CAST(:bankAccountNumber AS text)), '%'))
          AND
            (:bankIFSC IS NULL OR LOWER(d.bankIFSC) LIKE CONCAT('%', LOWER(CAST(:bankIFSC AS text)), '%'))
          """,
          countQuery = """
          SELECT COUNT(d) 
          FROM Dealer d
          WHERE
            (:name IS NULL OR LOWER(d.name) LIKE CONCAT('%', LOWER(CAST(:name AS text)), '%'))
          AND
            (:gstNumber IS NULL OR LOWER(d.gstNumber) LIKE CONCAT('%', LOWER(CAST(:gstNumber AS text)), '%'))
          AND
            (:panNumber IS NULL OR LOWER(d.panNumber) LIKE CONCAT('%', LOWER(CAST(:panNumber AS text)), '%'))
          
          AND
            (:address IS NULL OR LOWER(d.address) LIKE CONCAT('%', LOWER(CAST(:address AS text)), '%'))
          AND
            (:city IS NULL OR LOWER(d.city) LIKE CONCAT('%', LOWER(CAST(:city AS text)), '%'))
          AND
            (:state IS NULL OR LOWER(d.state) LIKE CONCAT('%', LOWER(CAST(:state AS text)), '%'))
          AND
            (:zipcode IS NULL OR LOWER(d.zipcode) LIKE CONCAT('%', LOWER(CAST(:zipcode AS text)), '%'))
          AND
            (:country IS NULL OR LOWER(d.country) LIKE CONCAT('%', LOWER(CAST(:country AS text)), '%'))
          
          
          AND
            (:contactPersonName IS NULL OR LOWER(d.contactPersonName) LIKE CONCAT('%', LOWER(CAST(:contactPersonName AS text)), '%'))
          AND
            (:contactPersonPhone IS NULL OR LOWER(d.contactPersonPhone) LIKE CONCAT('%', LOWER(CAST(:contactPersonPhone AS text)), '%'))
          AND
            (:contactPersonEmail IS NULL OR LOWER(d.contactPersonEmail) LIKE CONCAT('%', LOWER(CAST(:contactPersonEmail AS text)), '%'))
          
          
      
          AND
            (:phoneNumber IS NULL OR LOWER(d.phoneNumber) LIKE CONCAT('%', LOWER(CAST(:phoneNumber AS text)), '%'))
          AND
            (:website IS NULL OR LOWER(d.website) LIKE CONCAT('%', LOWER(CAST(:website AS text)), '%'))
          AND
            (:email IS NULL OR LOWER(d.email) LIKE CONCAT('%', LOWER(CAST(:email AS text)), '%'))
          AND
            (:bankName IS NULL OR LOWER(d.bankName) LIKE CONCAT('%', LOWER(CAST(:bankName AS text)), '%'))
          AND
            (:bankAccountNumber IS NULL OR LOWER(d.bankAccountNumber) LIKE CONCAT('%', LOWER(CAST(:bankAccountNumber AS text)), '%'))
          AND
            (:bankIFSC IS NULL OR LOWER(d.bankIFSC) LIKE CONCAT('%', LOWER(CAST(:bankIFSC AS text)), '%'))
          """)
  Page<DealerResponseDto> findAllByPage(
          Pageable pageable,

          String name,
          String gstNumber,
          String panNumber,

          String address,
          String city,
          String state,
          String zipcode,
          String country,

          String contactPersonName,
          String contactPersonPhone,
          String contactPersonEmail,

          String phoneNumber,
          String website,
          String email,
          String bankName,
          String bankAccountNumber,
          String bankIFSC
  );
}
