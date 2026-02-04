package com.aziz_motors.Web_Based.Garage.Management.System.service;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Appointment;
import com.aziz_motors.Web_Based.Garage.Management.System.entity.Customer;
import com.aziz_motors.Web_Based.Garage.Management.System.entity.Vehicle;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.DuplicateAppointmentException;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.IdNotFoundException;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.VehicleAlreadyAssignedException;
import com.aziz_motors.Web_Based.Garage.Management.System.repository.AppointmentRepository;
import com.aziz_motors.Web_Based.Garage.Management.System.repository.CustomerRepository;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.AppointmentRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.AppointmentResponseDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.CustomerResponseDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.VehicleResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AppointmentService {

    private AppointmentRepository appointmentRepository;
    private CustomerService customerService;
    private VehicleService vehicleService;
    private CustomerRepository customerRepository;


    @Autowired
    AppointmentService(CustomerService customerService,
                       VehicleService vehicleService,
                       CustomerRepository customerRepository,
                       AppointmentRepository appointmentRepository){

        this.customerService = customerService;
        this.vehicleService = vehicleService;
        this.customerRepository = customerRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Transactional
    public void addAppointment(AppointmentRequestDto dto){

        Customer customer = customerService.findOrCreate(dto.getCustomer());
        Vehicle vehicle = vehicleService.findOrCreate(dto.getVehicle());

        if (vehicle.getCustomer() != null && !vehicle.getCustomer().equals(customer)) {
            throw new VehicleAlreadyAssignedException();
        }

        customer.addVehicle(vehicle);

        if(appointmentRepository.existsByCustomerAndVehicleAndDateTimeAndServiceType(
                customer, vehicle, dto.getDateTime(), dto.getServiceType())
        ){
            throw new DuplicateAppointmentException();
        }

        Appointment appointment = fromDto(dto);
        appointment.setVehicle(vehicle);
        customer.addAppointment(appointment);

        customerRepository.save(customer);

    }

    public AppointmentResponseDto getAppointmentById(UUID id){
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(()->
                new IdNotFoundException("Appointment with id-"+id + " not found."));

        return toDto(appointment);
    }

    private AppointmentResponseDto toDto(Appointment appointment){
        Customer customer = appointment.getCustomer();
        Vehicle vehicle = appointment.getVehicle();

        CustomerResponseDto customerDto = new CustomerResponseDto();
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        customerDto.setAddress(customer.getAddress());

        VehicleResponseDto vehicleDto = new VehicleResponseDto();
        vehicleDto.setManufacturerName(vehicle.getManufacturerName());
        vehicleDto.setModelName(vehicle.getModelName());
        vehicleDto.setModelYear(vehicle.getModelYear());
        vehicleDto.setRegistrationNumber(vehicle.getRegistrationNumber());
        vehicleDto.setFuelType(vehicle.getFuelType());



        AppointmentResponseDto appointmentDto = new AppointmentResponseDto();

        appointmentDto.setServiceType(appointment.getServiceType());
        appointmentDto.setDateTime(appointment.getDateTime());
        appointmentDto.setStatus(appointment.getStatus());
        appointmentDto.setCreatedAt(appointment.getCreatedAt());
        appointmentDto.setUpdatedAt(appointment.getUpdatedAt());

        appointmentDto.setCustomer(customerDto);
        appointmentDto.setVehicle(vehicleDto);

        return appointmentDto;
    }

    private Appointment fromDto(AppointmentRequestDto dto){
        Appointment appointment = new Appointment();

        appointment.setDateTime(dto.getDateTime());
        appointment.setServiceType(dto.getServiceType());

        return appointment;
    }
}
