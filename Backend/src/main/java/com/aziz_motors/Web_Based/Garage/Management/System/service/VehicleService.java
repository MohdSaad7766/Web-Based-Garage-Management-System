package com.aziz_motors.Web_Based.Garage.Management.System.service;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Customer;
import com.aziz_motors.Web_Based.Garage.Management.System.entity.Vehicle;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.ResourceWithProvidedIdNotFoundException;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.VehicleAlreadyAssignedException;
import com.aziz_motors.Web_Based.Garage.Management.System.repository.CustomerRepository;
import com.aziz_motors.Web_Based.Garage.Management.System.repository.VehicleRepository;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.VehicleRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

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


    private Vehicle fromDto(VehicleRequestDto dto) {
        Vehicle vehicle = new Vehicle();

        vehicle.setManufacturerName(dto.getManufacturerName());
        vehicle.setModelName(dto.getModelName());
        vehicle.setModelYear(dto.getModelYear());
        vehicle.setFuelType(dto.getFuelType());
        vehicle.setRegistrationNumber(dto.getRegistrationNumber());

        return vehicle;
    }
}

