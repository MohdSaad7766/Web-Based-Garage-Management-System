package com.aziz_motors.Web_Based.Garage.Management.System.service;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Appointment;
import com.aziz_motors.Web_Based.Garage.Management.System.entity.Customer;
import com.aziz_motors.Web_Based.Garage.Management.System.entity.Vehicle;
import com.aziz_motors.Web_Based.Garage.Management.System.enums.AppointmentStatus;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.DuplicateAppointmentException;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.ResourceWithProvidedIdNotFoundException;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.VehicleAlreadyAssignedException;
import com.aziz_motors.Web_Based.Garage.Management.System.repository.AppointmentRepository;
import com.aziz_motors.Web_Based.Garage.Management.System.repository.CustomerRepository;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.AppointmentRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.FullAppointmentResponseDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.CustomerResponseDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.PaginatedResponse;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.VehicleResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AppointmentService {

    private AppointmentRepository appointmentRepository;
    private CustomerService customerService;
    private VehicleService vehicleService;
    private CustomerRepository customerRepository;
    private MailService mailService;

    @Value("${page.size}")
    private int PAGE_SIZE;


    @Autowired
    AppointmentService(CustomerService customerService,
                       VehicleService vehicleService,
                       CustomerRepository customerRepository,
                       AppointmentRepository appointmentRepository,
                       MailService mailService){

        this.customerService = customerService;
        this.vehicleService = vehicleService;
        this.customerRepository = customerRepository;
        this.appointmentRepository = appointmentRepository;
        this.mailService = mailService;
    }

    @Transactional
    public void addAppointment(AppointmentRequestDto dto) {

        // 1️⃣ Get or create Customer (persisted)
        Customer customer = customerService.findOrCreate(dto.getCustomer());

        // 2️⃣ Get or create Vehicle (persisted)
        Vehicle vehicle = vehicleService.findOrCreate(dto.getVehicle());

        // 3️⃣ Ownership validation
        if (vehicle.getCustomer() != null && !vehicle.getCustomer().equals(customer)) {
            throw new VehicleAlreadyAssignedException(
                    "Vehicle already belongs to another customer"
            );
        }

        // 4️⃣ Attach vehicle to customer
        customer.addVehicle(vehicle);

        // 5️⃣ Prevent duplicate appointment
        boolean exists = appointmentRepository
                .existsByCustomerAndVehicleAndDateTimeAndServiceType(
                        customer,
                        vehicle,
                        dto.getDateTime(),
                        dto.getServiceType()
                );

        if (exists) {
            throw new DuplicateAppointmentException(
                    "Appointment already exists for this vehicle and time"
            );
        }

        // 6️⃣ Create appointment
        Appointment appointment = new Appointment();
        appointment.setDateTime(dto.getDateTime());
        appointment.setServiceType(dto.getServiceType());
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setVehicle(vehicle);

        // 7️⃣ Attach appointment to customer
        customer.addAppointment(appointment);

        // 8️⃣ Save ONLY customer (cascade saves appointment)
        customerRepository.save(customer);

        mailService.sendAppointmentMail(toDto(appointment), AppointmentStatus.PENDING);
    }


    public FullAppointmentResponseDto getAppointmentById(UUID id){
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(()->
                new ResourceWithProvidedIdNotFoundException("Appointment with id-"+id + " not found."));

        return toDto(appointment);
    }


    public PaginatedResponse<FullAppointmentResponseDto> getAppointments(int pageNo){
        Sort sort = Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, sort);

        Page<Appointment> page = appointmentRepository.findAll(pageable);

        List<FullAppointmentResponseDto> content = new ArrayList<>();

        for(Appointment appointment : page.getContent()){
            content.add(toDto(appointment));
        }

        return new PaginatedResponse<>(
                content,
                pageNo,
                page.getTotalPages(),
                page.getTotalElements()
        );
    }

    public void updateAppointmentStatus(UUID id, AppointmentStatus status, boolean sentMail){
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(()->
                        new ResourceWithProvidedIdNotFoundException("Appointment with id-"+id+" not found...")
                );
        appointment.setStatus(status);

        appointment = appointmentRepository.save(appointment);

        if(sentMail){
            mailService.sendAppointmentMail(toDto(appointment), status);
        }
    }

    private FullAppointmentResponseDto toDto(Appointment appointment){
        Customer customer = appointment.getCustomer();
        Vehicle vehicle = appointment.getVehicle();

        CustomerResponseDto customerDto = new CustomerResponseDto();
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        customerDto.setAddress(customer.getAddress());
        customerDto.setId(customer.getId());

        VehicleResponseDto vehicleDto = new VehicleResponseDto();
        vehicleDto.setManufacturerName(vehicle.getManufacturerName());
        vehicleDto.setModelName(vehicle.getModelName());
        vehicleDto.setModelYear(vehicle.getModelYear());
        vehicleDto.setRegistrationNumber(vehicle.getRegistrationNumber());
        vehicleDto.setFuelType(vehicle.getFuelType());
        vehicleDto.setVehicleId(vehicle.getId());


        FullAppointmentResponseDto appointmentDto = new FullAppointmentResponseDto();

        appointmentDto.setServiceType(appointment.getServiceType());
        appointmentDto.setDateTime(appointment.getDateTime());
        appointmentDto.setStatus(appointment.getStatus());
        appointmentDto.setCreatedAt(appointment.getCreatedAt());
        appointmentDto.setUpdatedAt(appointment.getUpdatedAt());

        appointmentDto.setCustomer(customerDto);
        appointmentDto.setVehicle(vehicleDto);
        appointmentDto.setAppointmentId(appointment.getId());

        return appointmentDto;
    }

    private Appointment fromDto(AppointmentRequestDto dto){
        Appointment appointment = new Appointment();

        appointment.setDateTime(dto.getDateTime());
        appointment.setServiceType(dto.getServiceType());

        return appointment;
    }
}
