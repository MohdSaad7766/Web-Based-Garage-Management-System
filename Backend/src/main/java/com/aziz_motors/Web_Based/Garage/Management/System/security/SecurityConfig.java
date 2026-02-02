package com.aziz_motors.Web_Based.Garage.Management.System.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration configuration){
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider getAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(getPasswordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain getFilterChain(HttpSecurity httpSecurity){
        return httpSecurity.build();
    }
}
