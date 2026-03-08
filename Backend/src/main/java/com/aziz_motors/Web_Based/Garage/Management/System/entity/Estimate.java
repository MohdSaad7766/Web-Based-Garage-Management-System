package com.aziz_motors.Web_Based.Garage.Management.System.entity;

import com.aziz_motors.Web_Based.Garage.Management.System.enums.EstimateStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class Estimate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String estimateNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstimateStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "estimate", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<EstimateItem> estimateItems = new ArrayList<>();

    @Column(nullable = false)
    private LocalDate issueDate;

    private LocalDate validUntil;

    @Column(length = 1000)
    private String notes;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal grandTotal = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
