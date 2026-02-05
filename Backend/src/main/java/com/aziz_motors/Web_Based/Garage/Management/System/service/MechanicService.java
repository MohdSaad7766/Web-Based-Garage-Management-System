package com.aziz_motors.Web_Based.Garage.Management.System.service;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Mechanic;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.MechanicWithEmailAlreadyRegisteredException;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.ResourceWithProvidedIdNotFoundException;
import com.aziz_motors.Web_Based.Garage.Management.System.repository.MechanicRepository;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.MechanicRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.MechanicResponseDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.PaginatedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MechanicService {
    private final MechanicRepository mechanicRepository;
    private final int PAGE_SIZE = 10;

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

    public MechanicResponseDto getMechanicById(UUID id){
        Mechanic mechanic = mechanicRepository.findById(id).orElseThrow(()->
                new ResourceWithProvidedIdNotFoundException("Mechanic with id-"+id+" not found..."));

        return toMechanicResponseDto(mechanic);

    }

    public PaginatedResponse<MechanicResponseDto> getMechanics(int pageNo){
        Sort sort = Sort.by("createdAt").ascending();
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, sort);

        Page<MechanicResponseDto> page = mechanicRepository.findAllByPage(pageable);

        return new PaginatedResponse<>(
                page.getContent(),
                pageNo,
                page.getTotalPages(),
                page.getTotalElements()
        );
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
