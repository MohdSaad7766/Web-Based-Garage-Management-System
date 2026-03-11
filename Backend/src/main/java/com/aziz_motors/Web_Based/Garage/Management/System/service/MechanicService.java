package com.aziz_motors.Web_Based.Garage.Management.System.service;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Mechanic;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.MechanicWithEmailAlreadyRegisteredException;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.ResourceWithProvidedIdNotFoundException;
import com.aziz_motors.Web_Based.Garage.Management.System.repository.MechanicRepository;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.MechanicRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.MechanicResponseDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.PaginatedResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class MechanicService {
    private final MechanicRepository mechanicRepository;
    @Value("${page.size}")
    private int PAGE_SIZE;

    MechanicService(MechanicRepository mechanicRepository){
        this.mechanicRepository = mechanicRepository;
    }

    public UUID addMechanic(MechanicRequestDto dto){
        if(mechanicRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new MechanicWithEmailAlreadyRegisteredException();
        }

        Mechanic mechanic = fromDto(dto);

        return mechanicRepository.save(mechanic).getId();
    }

    public MechanicResponseDto getMechanicResponseById(UUID id){
        Mechanic mechanic = mechanicRepository.findById(id).orElseThrow(()->
                new ResourceWithProvidedIdNotFoundException("Mechanic with id-"+id+" not found..."));

        return toMechanicResponseDto(mechanic);

    }

    public Mechanic getMechanicById(UUID id){
        return mechanicRepository.findById(id).orElseThrow(()->
                new ResourceWithProvidedIdNotFoundException("Mechanic with id-"+id+" not found..."));

    }


    public PaginatedResponse<MechanicResponseDto> getMechanics(
            int pageNo,
            String name,
            String email,
            String mobileNumber,
            Double minSalary,
            Double maxSalary,
            String address,
            LocalDate joinDate
    ){
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, sort);

        Page<MechanicResponseDto> page = mechanicRepository.findAllByPage(
                pageable,
                name,
                email,
                mobileNumber,
                minSalary,
                maxSalary,
                address,
                joinDate
        );

        return new PaginatedResponse<>(
                page.getContent(),
                pageNo,
                page.getTotalPages(),
                page.getTotalElements()
        );
    }

    @Transactional
    public void deleteMechanic(UUID id){
        Mechanic mechanic =  getMechanicById(id);

        mechanicRepository.delete(mechanic);
    }

    private MechanicResponseDto toMechanicResponseDto(Mechanic mechanic){
        MechanicResponseDto dto = new MechanicResponseDto();

        dto.setName(mechanic.getName());
        dto.setEmail(mechanic.getEmail());
        dto.setId(mechanic.getId());
        dto.setAddress(mechanic.getAddress());
        dto.setSalary(mechanic.getSalary());
        dto.setJoinDate(mechanic.getJoinDate());
        dto.setMobileNumber(mechanic.getMobileNumber());

        return dto;
    }
    private Mechanic fromDto(MechanicRequestDto dto){
        Mechanic mechanic = new Mechanic();

        mechanic.setName(dto.getName());
        mechanic.setEmail(dto.getEmail());
        mechanic.setAddress(dto.getAddress());
        mechanic.setMobileNumber(dto.getMobileNumber());
        mechanic.setSalary(dto.getSalary());
        mechanic.setJoinDate(dto.getJoinDate());


        return mechanic;
    }
}
