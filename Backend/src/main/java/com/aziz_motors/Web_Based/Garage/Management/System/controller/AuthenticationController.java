package com.aziz_motors.Web_Based.Garage.Management.System.controller;

import com.aziz_motors.Web_Based.Garage.Management.System.requestDtos.LoginRequestDto;
import com.aziz_motors.Web_Based.Garage.Management.System.responseDtos.LoginResponseDTO;
import com.aziz_motors.Web_Based.Garage.Management.System.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    AuthenticationController(AuthenticationManager authenticationManager, JwtService jwtService){
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> adminLogin(
            @RequestBody LoginRequestDto dto) {

        LoginResponseDTO responseDTO = new LoginResponseDTO(false, null);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getEmail(), dto.getPassword()
                    )
            );

            responseDTO.setAuthenticated(true);
            responseDTO.setToken(jwtService.generateToken(dto.getEmail()));

            return ResponseEntity.ok(responseDTO);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDTO);
        }
    }

}
