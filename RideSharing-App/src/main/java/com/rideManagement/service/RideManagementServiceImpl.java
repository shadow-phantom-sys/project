package com.rideManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rideManagement.model.RideManagement;
import com.rideManagement.repository.RideManagementRepository;

@Service
public class RideManagementServiceImpl implements RideManagementService {

	@Autowired
	private RideManagementRepository ridemanagementRepo;
	
	@Override
	public List<RideManagement> getAllRidemanagement(){
		return ridemanagementRepo.findAll();
		
	}
	@Override
	public RideManagement getRideManagementById(int id) {
		Optional<RideManagement> optRide=ridemanagementRepo.findById(id);
		RideManagement ridemanagement=optRide.get();
		return ridemanagementRepo.findById(id).get();
		
	}
	@Override
	public void saveRideManagement(RideManagement ridemanagement) {
		ridemanagementRepo.save(ridemanagement);
	}
	@Override
	public void deleteRideManagement(int id) {
		ridemanagementRepo.deleteById(id);
	}
	
	
	
	
}





















