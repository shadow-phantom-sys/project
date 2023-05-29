package com.rideManagement.service;

import java.util.List;

import com.rideManagement.model.RideManagement;

public interface RideManagementService {

	List<RideManagement> getAllRidemanagement();

	RideManagement getRideManagementById(int id);

	void saveRideManagement(RideManagement ridemanagement);

	void deleteRideManagement(int id);

}