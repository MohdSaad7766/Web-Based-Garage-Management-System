package com.aziz_motors.Web_Based.Garage.Management.System.service;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Dealer;
import com.aziz_motors.Web_Based.Garage.Management.System.entity.PaymentReceipt;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.DealerAlreadyExistsException;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.ResourceWithProvidedIdNotFoundException;
import com.aziz_motors.Web_Based.Garage.Management.System.repository.DealerRepository;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.DealerRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.PaymentReceiptRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.DealerResponseDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.PaginatedResponse;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.PaymentReceiptResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DealerService {

    private final DealerRepository dealerRepository;
    @Value("${page.size}")
    private int PAGE_SIZE;

    public DealerService(DealerRepository dealerRepository){
        this.dealerRepository = dealerRepository;
    }

    @Transactional
    public UUID addDealer(DealerRequestDto dto){
        if(dealerRepository.findByNameOrEmailOrGstNumber(dto.getName(), dto.getEmail(), dto.getGstNumber()).isPresent()){
            throw new DealerAlreadyExistsException();
        }
        Dealer dealer = fromDto(dto);
        return dealerRepository.save(dealer).getId();
    }


    public DealerResponseDto getDealerById(UUID id){
        Dealer dealer = dealerRepository.findById(id).orElseThrow(()->
                new ResourceWithProvidedIdNotFoundException("Dealer with id-"+id+" not found."));

        return toDto(dealer);
    }


    public PaginatedResponse<DealerResponseDto> getDealers(int pageNo){
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, sort);

        Page<DealerResponseDto> page = dealerRepository.findAllByPage(pageable);


        return new PaginatedResponse<>(
                page.getContent(),
                pageNo,
                page.getTotalPages(),
                page.getTotalElements()
        );
    }

    private DealerResponseDto toDto(Dealer dealer){
        DealerResponseDto dto = new DealerResponseDto();

        dto.setId(dealer.getId());
        dto.setName(dealer.getName());
        dto.setGstNumber(dealer.getGstNumber());
        dto.setPanNumber(dealer.getPanNumber());
        dto.setAddress(dealer.getAddress());
        dto.setCity(dealer.getCity());
        dto.setState(dealer.getState());
        dto.setZipcode(dealer.getZipcode());
        dto.setCountry(dealer.getCountry());
        dto.setContactPersonName(dealer.getContactPersonName());
        dto.setContactPersonEmail(dealer.getContactPersonEmail());
        dto.setContactPersonPhone(dealer.getContactPersonPhone());
        dto.setPhoneNumber(dealer.getPhoneNumber());
        dto.setEmail(dealer.getEmail());
        dto.setWebsite(dealer.getWebsite());
        dto.setBankIFSC(dealer.getBankIFSC());
        dto.setBankName(dealer.getBankName());
        dto.setBankAccountNumber(dealer.getBankAccountNumber());
        dto.setPaymentReceipts(toDto(dealer.getPaymentReceipts()));

        return dto;
    }

    private List<PaymentReceiptResponseDto> toDto(List<PaymentReceipt> paymentReceipts){
        List<PaymentReceiptResponseDto> dtos = new ArrayList<>();

        for(PaymentReceipt paymentReceipt : paymentReceipts){
            dtos.add(toDto(paymentReceipt));
        }

        return dtos;
    }

    private PaymentReceiptResponseDto toDto(PaymentReceipt paymentReceipt){
        PaymentReceiptResponseDto dto = new PaymentReceiptResponseDto();

        dto.setId(paymentReceipt.getId());
        dto.setAmountInWords(paymentReceipt.getAmountInWords());
        dto.setAmount(paymentReceipt.getAmount());
        dto.setPayerName(paymentReceipt.getPayerName());
        dto.setPayeeName(paymentReceipt.getPayeeName());
        dto.setPaymentDate(paymentReceipt.getPaymentDate());
        dto.setPaymentType(paymentReceipt.getPaymentType());
        dto.setReceiptNumber(paymentReceipt.getReceiptNumber());
        dto.setDealerId(paymentReceipt.getDealer().getId());

        return dto;
    }

    private Dealer fromDto(DealerRequestDto dto){
        Dealer dealer = new Dealer();

        dealer.setName(dto.getName());
        dealer.setEmail(dto.getEmail());
        dealer.setWebsite(dto.getWebsite());
        dealer.setGstNumber(dto.getGstNumber());
        dealer.setPhoneNumber(dto.getPhoneNumber());
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
        dealer.setPaymentReceipts(fromDto(dto.getPaymentReceipts(), dealer));

        return dealer;
    }

    private List<PaymentReceipt> fromDto(List<PaymentReceiptRequestDto> dtos, Dealer dealer){
        List<PaymentReceipt> paymentReceipts = new ArrayList<>();

        for (PaymentReceiptRequestDto dto : dtos){
            paymentReceipts.add(fromDto(dto, dealer));
        }
        return paymentReceipts;
    }

    private PaymentReceipt fromDto(PaymentReceiptRequestDto dto, Dealer dealer){
        PaymentReceipt paymentReceipt = new PaymentReceipt();

        paymentReceipt.setReceiptNumber(dto.getReceiptNumber());
        paymentReceipt.setAmount(dto.getAmount());
        paymentReceipt.setAmountInWords(dto.getAmountInWords());
        paymentReceipt.setPaymentDate(dto.getPaymentDate());
        paymentReceipt.setPaymentType(dto.getPaymentType());
        paymentReceipt.setPayerName(dto.getPayerName());
        paymentReceipt.setPayeeName(dto.getPayeeName());
        paymentReceipt.setDealer(dealer);

        return paymentReceipt;
    }
}
