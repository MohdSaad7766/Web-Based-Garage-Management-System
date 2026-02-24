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


  @Query("""
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
            d.bankIFSC            
          )
          FROM Dealer d
          """)
  Page<DealerResponseDto> findAllByPage(Pageable pageable);
}
