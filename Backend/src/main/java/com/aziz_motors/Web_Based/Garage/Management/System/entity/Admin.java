package com.aziz_motors.Web_Based.Garage.Management.System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String mobileNumber;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "is_email_verified")
    private boolean isEmailVerified;
}
