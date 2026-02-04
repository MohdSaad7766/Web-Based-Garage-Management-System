package com.aziz_motors.Web_Based.Garage.Management.System.service;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Appointment;
import com.aziz_motors.Web_Based.Garage.Management.System.entity.Customer;
import com.aziz_motors.Web_Based.Garage.Management.System.entity.Vehicle;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.DuplicateAppointmentException;
import com.aziz_motors.Web_Based.Garage.Management.System.exception.VehicleAlreadyAssignedException;
import com.aziz_motors.Web_Based.Garage.Management.System.repository.AppointmentRepository;
import com.aziz_motors.Web_Based.Garage.Management.System.repository.CustomerRepository;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.AppointmentRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private Appointment fromDto(AppointmentRequestDto dto){
        Appointment appointment = new Appointment();

        appointment.setDateTime(dto.getDateTime());
        appointment.setServiceType(dto.getServiceType());

        return appointment;
    }
}
