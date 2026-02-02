package com.aziz_motors.Web_Based.Garage.Management.System.security;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.Admin;
import com.aziz_motors.Web_Based.Garage.Management.System.repository.AdminRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private AdminRepository adminRepository;

    UserDetailsServiceImpl(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Admin> admin = adminRepository.findAdminByEmail(email);

        if(admin.isPresent()){
            return new UserDetailsImpl(admin.get());
        }
        else{
            throw new UsernameNotFoundException("User with email- "+email+" not found!!!");
        }
    }
}
