package com.rideManagement.DAL;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rideManagement.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, String>{

}
