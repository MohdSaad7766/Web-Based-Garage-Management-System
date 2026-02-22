package com.aziz_motors.Web_Based.Garage.Management.System.service;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Dealer;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.DealerAlreadyExistsException;
import com.aziz_motors.Web_Based.Garage.Management.System.repository.DealerRepository;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.DealerRequestDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DealerService {

    private final DealerRepository dealerRepository;

    public DealerService(DealerRepository dealerRepository){
        this.dealerRepository = dealerRepository;
    }

    public UUID addDealer(DealerRequestDto dto){
        if(dealerRepository.findByNameOrEmailOrGstNumber(dto.getName(), dto.getEmail(), dto.getGstNumber()).isPresent()){
            throw new DealerAlreadyExistsException();
        }
        Dealer dealer = fromDto(dto);
        return dealerRepository.save(dealer).getId();
    }



    private Dealer fromDto(DealerRequestDto dto){
        Dealer dealer = new Dealer();

        dealer.setName(dto.getName());
        dealer.setEmail(dto.getEmail());
        dealer.setWebsite(dto.getWebsite());
        dealer.setGstNumber(dto.getGstNumber());
        dealer.setPhoneNumbers(dto.getPhoneNumbers());
        dealer.setPanNumber(dto.getPanNumber());

        dealer.setContactPersonName(dto.getContactPersonName());
        dealer.setContactPersonEmail(dto.getContactPersonEmail());
        dealer.setContactPersonPhone(dto.getContactPersonPhone());

        dealer.setAddress(dto.getAddress());
        dealer.setCity(dto.getCity());
        dealer.setState(dto.getState());
        dealer.setCountry(dto.getCountry());
        dealer.setZipcode(dto.getZipcode());

        dealer.setBankName(dto.getBankName());
        dealer.setBankIFSC(dto.getBankIFSC());
        dealer.setBankAccountNumber(dto.getBankAccountNumber());

        return dealer;
    }
}
