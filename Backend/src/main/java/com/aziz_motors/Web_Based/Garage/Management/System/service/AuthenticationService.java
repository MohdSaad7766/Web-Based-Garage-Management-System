package com.aziz_motors.Web_Based.Garage.Management.System.service;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Admin;
import com.aziz_motors.Web_Based.Garage.Management.System.repository.AdminRepository;
import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.RegistrationRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private AdminRepository adminRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationService(AdminRepository adminRepository, PasswordEncoder passwordEncoder){
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerAdmin(RegistrationRequestDto dto){
        Admin admin = Admin.fromDTO(dto);
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        adminRepository.save(admin);
    }
}
