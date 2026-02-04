package com.aziz_motors.Web_Based.Garage.Management.System.repository;

import com.aziz_motors.Web_Based.Garage.Management.System.entity.EstimateItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EstimateItemRepository extends JpaRepository<EstimateItem, UUID> {
}