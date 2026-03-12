package com.aziz_motors.Web_Based.Garage.Management.System.service;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Customer;
import com.aziz_motors.Web_Based.Garage.Management.System.entity.Vehicle;
import com.aziz_motors.Web_Based.Garage.Management.System.enums.FuelType;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.ResourceWithProvidedIdNotFoundException;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.VehicleAlreadyAssignedException;
import com.aziz_motors.Web_Based.Garage.Management.System.repository.CustomerRepository;
import com.aziz_motors.Web_Based.Garage.Management.System.repository.VehicleRepository;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.VehicleRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.PaginatedResponse;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.VehicleResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    @Value("${page.size}")
    private int PAGE_SIZE;

    public VehicleService(VehicleRepository vehicleRepository,
                          CustomerService customerService,
                          CustomerRepository customerRepository) {
        this.vehicleRepository = vehicleRepository;
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }

    public Vehicle findOrCreate(VehicleRequestDto dto) {
        return vehicleRepository
                .findByRegistrationNumber(dto.getRegistrationNumber())
                .orElseGet(() -> vehicleRepository.save(fromDto(dto)));
    }

    @Transactional
    public void addVehicle(UUID customerId, VehicleRequestDto dto){
        Customer customer = customer = customerRepository.findById(customerId)
                .orElseThrow(()->
                        new ResourceWithProvidedIdNotFoundException("Customer with id- "+customerId + " not found...")
                );

        if(vehicleRepository.findByRegistrationNumber(dto.getRegistrationNumber()).isPresent()){
            throw new VehicleAlreadyAssignedException("Vehicle already belongs to another customer");
        }

        Vehicle vehicle = fromDto(dto);
        vehicle.setCustomer(customer);
        customer.getVehicles().add(vehicle);


        customerRepository.save(customer);
    }

    public Vehicle getVehicleById(UUID id){
        return vehicleRepository.findById(id).orElseThrow(()->
                new ResourceWithProvidedIdNotFoundException("Vehicle with id-"+id+" not found."));
    }

    @Transactional(readOnly = true)
    public VehicleResponseDto getVehicleResponseById(UUID id){
        return toDto(getVehicleById(id));
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<VehicleResponseDto> getVehicles(int pageNo,
                                         String manufacturerName,
                                         String modelName,
                                         Integer modelYear,
                                         String registrationNumber,
                                         FuelType fuelType,
                                         UUID customerId
    ){

        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(pageNo,PAGE_SIZE, sort);
        Page<VehicleResponseDto> page = vehicleRepository.findVehicleByPage(
                pageable,
                manufacturerName,
                modelName,
                modelYear,
                registrationNumber,
                fuelType,
                customerId
        );

        return new PaginatedResponse<>(
                page.getContent(),
                pageNo,
                page.getTotalPages(),
                page.getTotalElements()
        );

    }



    @Transactional
    public void deleteVehicle(UUID id){
        Vehicle vehicle = getVehicleById(id);

        vehicleRepository.delete(vehicle);
    }

    private Vehicle fromDto(VehicleRequestDto dto) {
        Vehicle vehicle = new Vehicle();

        vehicle.setManufacturerName(dto.getManufacturerName());
        vehicle.setModelName(dto.getModelName());
        vehicle.setModelYear(dto.getModelYear());
        vehicle.setFuelType(dto.getFuelType());
        vehicle.setRegistrationNumber(dto.getRegistrationNumber());

        return vehicle;
    }

    private VehicleResponseDto toDto(Vehicle vehicle){
        VehicleResponseDto dto = new VehicleResponseDto();

        dto.setVehicleId(vehicle.getId());
        dto.setCustomerId(vehicle.getCustomer().getId());
        dto.setModelName(vehicle.getModelName());
        dto.setFuelType(vehicle.getFuelType());
        dto.setManufacturerName(vehicle.getManufacturerName());
        dto.setRegistrationNumber(vehicle.getRegistrationNumber());
        dto.setModelYear(vehicle.getModelYear());

        return dto;
    }
}

