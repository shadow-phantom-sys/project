package com.rideManagement.DAL.Interfaces;


import com.rideManagement.model.DTOs.SearchDTO;
import com.rideManagement.model.DTOs.SearchDTO;
import com.rideManagement.model.DTOs.SearchDTO;
import com.rideManagement.model.DTOs.SearchDTO;
import com.rideManagement.model.DTOs.SearchDTO;
import com.rideManagement.model.*;
import com.rideManagement.model.DTOs.*;
import java.util.List;
import java.util.ArrayList;

public interface IRideRepository {
    List<Distance> getDistances();
    int getFare(String regNo);
    int addRide(RideSchedules rideSchedule);
    int getMotoristId(String regNo);
    List<RideSchedules> search(SearchDTO searchDTO);
    int bookRide(Booking booking);
    int getRideFare(FareDTO fareDTO);
    String getRegNo(int userId);
}
