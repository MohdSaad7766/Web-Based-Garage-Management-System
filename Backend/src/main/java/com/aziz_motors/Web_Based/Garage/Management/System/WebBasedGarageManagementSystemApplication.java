package com.aziz_motors.Web_Based.Garage.Management.System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class WebBasedGarageManagementSystemApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(WebBasedGarageManagementSystemApplication.class, args);
		System.out.println("Spring Application Has Been Started...");
//		AuthenticationService authenticationService = context.getBean(AuthenticationService.class);
//		AdminRegistrationRequestDto dto = new AdminRegistrationRequestDto();
//		dto.setEmail("azizmotorgarage1995@gmail.com");
//		dto.setPassword("S@@d1234");
//		dto.setName("Ansari Saad");
//		dto.setMobileNumber("+91 9175715785");
//		authenticationService.registerAdmin(dto);
	}
}
