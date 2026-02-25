package com.aziz_motors.Web_Based.Garage.Management.System.service;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Appointment;
import com.aziz_motors.Web_Based.Garage.Management.System.entity.Customer;
import com.aziz_motors.Web_Based.Garage.Management.System.entity.Estimate;
import com.aziz_motors.Web_Based.Garage.Management.System.entity.Vehicle;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.CustomerWithEmailAlreadyRegisteredException;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.ResourceWithProvidedIdNotFoundException;
import com.aziz_motors.Web_Based.Garage.Management.System.repository.CustomerRepository;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.CustomerRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    @Value("${page.size}")
    private int PAGE_SIZE;
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer findOrCreate(CustomerRequestDto dto) {
        return customerRepository
                .findByEmail(dto.getEmail())
                .orElseGet(() -> customerRepository.save(fromDto(dto)));
    }


    public UUID addCustomer(CustomerRequestDto dto){
        if(customerRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new CustomerWithEmailAlreadyRegisteredException(
                    "Email- "+dto.getEmail()+" belongs to an another customer or the customer is already registered.");
        }

        Customer customer = fromDto(dto);

        return customerRepository.save(customer).getId();
    }



    public FullCustomerResponseDto getCustomerById(UUID customerId){
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()->
                        new ResourceWithProvidedIdNotFoundException("Customer with id- "+customerId + " not found...")
                );

        return toFullCustomerResponseDto(customer);
    }


    public PaginatedResponse<CustomerResponseDto> getCustomers(int pageNo){
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, sort);

        Page<CustomerResponseDto> page = customerRepository.findAllByPage(pageable);

        return new PaginatedResponse<>(
                page.getContent(),
                pageNo,
                page.getTotalPages(),
                page.getTotalElements());
    }
    private FullCustomerResponseDto toFullCustomerResponseDto(Customer customer){
        FullCustomerResponseDto dto = new FullCustomerResponseDto();

        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setMobileNumber(customer.getMobileNumber());
        dto.setAddress(customer.getAddress());

        List<VehicleResponseDto> vehicles = new ArrayList<>();
        for(Vehicle vehicle : customer.getVehicles()){
            vehicles.add(toVehicleResponseDto(vehicle));
        }
        dto.setVehicles(vehicles);

        List<AppointmentResponseDto> appointments = new ArrayList<>();
        for(Appointment appointment : customer.getAppointments()){
            appointments.add(toAppointmentResponseDto(appointment));
        }
        dto.setAppointments(appointments);

        List<EstimateResponseDto> estimates = new ArrayList<>();
        for(Estimate estimate : customer.getEstimates()){
            estimates.add(toEstimateResponseDto(estimate));
        }
        dto.setEstimates(estimates);

        return dto;
    }

    private VehicleResponseDto toVehicleResponseDto(Vehicle vehicle){
        VehicleResponseDto dto = new VehicleResponseDto();

        dto.setVehicleId(vehicle.getId());
        dto.setFuelType(vehicle.getFuelType());
        dto.setRegistrationNumber(vehicle.getRegistrationNumber());
        dto.setManufacturerName(vehicle.getManufacturerName());
        dto.setModelName(vehicle.getModelName());
        dto.setModelYear(vehicle.getModelYear());

        return dto;
    }

    private AppointmentResponseDto toAppointmentResponseDto(Appointment appointment){
        AppointmentResponseDto dto = new AppointmentResponseDto();

        dto.setAppointmentId(appointment.getId());
        dto.setServiceType(appointment.getServiceType());
        dto.setStatus(appointment.getStatus());
        dto.setDateTime(appointment.getDateTime());
        dto.setCreatedAt(appointment.getCreatedAt());
        dto.setUpdatedAt(appointment.getUpdatedAt());

        return dto;
    }

    private EstimateResponseDto toEstimateResponseDto(Estimate estimate){
        EstimateResponseDto dto = new EstimateResponseDto();


        // work is remaining
        return dto;
    }

    private Customer fromDto(CustomerRequestDto dto) {
        Customer customer = new Customer();
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setMobileNumber(dto.getMobileNumber());
        customer.setAddress(dto.getAddress());
        return customer;
    }
}

