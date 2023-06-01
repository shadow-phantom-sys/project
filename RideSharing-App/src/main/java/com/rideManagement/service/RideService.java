package com.rideManagement.service;
import AutoMapper;
import RideSharingPlatform.Context.RideDbContext;

import com.rideManagement.DAL.RideRepository;
import com.rideManagement.DAL.Interfaces.IRideRepository;
import com.rideManagement.model.Booking;
import com.rideManagement.model.Distance;
import com.rideManagement.model.RideSchedules;
import com.rideManagement.model.DTOs.*;
import RideSharingPlatform.Microservices.RideManagement.DAL.Interfaces.*;
import RideSharingPlatform.Microservices.RideManagement.Models.*;
import RideSharingPlatform.Microservices.RideManagement.Models.DTOs.*;
import java.util.List;
import java.util.ArrayList;

public class RideService {
    private final RideDbContext context;
    private final IRideRepository repository;
    private final IMapper mapper;

    public RideService(RideDbContext context, IMapper mapper) {
        this.context = context;
        this.repository = new RideRepository(context);
        this.mapper = mapper;
    }

    public List<Distance> getDistances() {
        return repository.getDistances();
    }

    public boolean scheduleRide(RideDTO ride) {
        Distance d = context.getDistances().stream()
                .filter(v -> v.getFrom().equals(ride.getRideFrom()) && v.getTo().equals(ride.getRideTo()))
                .findFirst()
                .orElse(null);

        RideSchedules rideSchedule = mapper.map(ride, RideSchedules.class);
        rideSchedule.setRideFare(repository.getFare(ride.getVehicleRegistrationNo()) * d.getDistanceInKMS());
        rideSchedule.setMotoristUserId(repository.getMotoristId(ride.getVehicleRegistrationNo()));

        int r = repository.addRide(rideSchedule);
        return r > 0;
    }

    public List<RideSchedules> searchRide(SearchDTO ride) {
        return repository.search(ride);
    }

    public int bookRide(BookingDTO bookingDTO) {
        Booking booking = mapper.map(bookingDTO, Booking.class);
        return repository.bookRide(booking);
    }

    public int getFare(FareDTO fare) {
        return repository.getRideFare(fare);
    }

    public String getRegNo(int userId) {
        return repository.getRegNo(userId);
    }
}
