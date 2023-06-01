package com.rideManagement.DAL;
import RideSharingPlatform.Context.RideDbContext;
import com.rideManagement.DAL.Interfaces.IRideRepository;
import com.rideManagement.model.*;
import com.rideManagement.model.DTOs.*;
import com.rideManagement.*;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class RideRepository implements IRideRepository {
    private final RideDbContext context;

    public RideRepository(RideDbContext context) {
        this.context = context;
    }

    public List<Distance> getDistances() {
        return new ArrayList<>(context.getDistances());
    }

    public int addRide(RideSchedule rideSchedule) {
        context.getRideSchedules().add(rideSchedule);
        return context.SaveChanges();
    }

    public int getRideFare(FareDTO fareDTO) {
        Distance d = context.getDistances().stream()
                .filter(distance -> distance.getID() == fareDTO.getDistanceID())
                .findFirst()
                .orElse(null);

        if (d == null) {
            return 0;
        }

        int f = getFare(fareDTO.getVehicleRegistrationNo()) * d.getDistanceInKMS();
        return f;
    }

    public int getFare(String regNo) {
        var vehicle = context.getVehicles().stream()
                .filter(v -> v.getRegistrationNo().equals(regNo))
                .findFirst()
                .orElse(null);

        if (vehicle == null) {
            return 0;
        }

        var vehicleType = context.getVehicleTypes().stream()
                .filter(vt -> vt.getID() == vehicle.getVehicleTypeId())
                .findFirst()
                .orElse(null);

        if (vehicleType == null) {
            return 0;
        }

        return vehicleType.getFarePerKM();
    }

    public int getMotoristId(String regNo) {
        var vehicle = context.getVehicles().stream()
                .filter(v -> v.getRegistrationNo().equals(regNo))
                .findFirst()
                .orElse(null);

        return (vehicle != null) ? vehicle.getBelongsToUserId() : 0;
    }

    public List<RideSchedules> search(SearchDTO searchDTO) {
        List<RideSchedules> rides = new ArrayList<>(context.getRideSchedules());

        if (searchDTO.getFrom() != null) {
            rides = rides.stream()
                    .filter(rs -> rs.getRideFrom().equals(searchDTO.getFrom()))
                    .collect(Collectors.toList());
        }

        if (searchDTO.getTo() != null) {
            rides = rides.stream()
                    .filter(rs -> rs.getRideTo().equals(searchDTO.getTo()))
                    .collect(Collectors.toList());
        }

        if (searchDTO.getMaxPrice() != null) {
            rides = rides.stream()
                    .filter(rs -> rs.getRideFare() <= searchDTO.getMaxPrice())
                    .collect(Collectors.toList());
        }

        if (searchDTO.getMinPrice() != null) {
            rides = rides.stream()
                    .filter(rs -> rs.getRideFare() >= searchDTO.getMinPrice())
                    .collect(Collectors.toList());
        }

        return rides;
    }

    public int bookRide(Booking booking) {
        RideSchedules rideSchedule = context.getRideSchedules().stream()
                .filter(rs -> rs.getID() == booking.getRideSchedulesID())
                .findFirst()
                .orElse(null);

        if (rideSchedule == null) {
            return -1;
        }

        booking.setRideSchedule(rideSchedule);
        booking.setTotalAmount(booking.getNoOfSeats() * rideSchedule.getRideFare());
        context.getBookings().add(booking);

        rideSchedule.setNoOfSeatsAvailable(rideSchedule.getNoOfSeatsAvailable() - booking.getNoOfSeats());
        context.SaveChanges();

        return booking.getBookingID();
    }

    public String getRegNo(int userId) {
        var vehicle = context.getVehicles().stream()
                .filter(v -> v.getBelongsToUserId() == userId)
                .findFirst()
                .orElse(null);

        return (vehicle != null) ? vehicle.getRegistrationNo() : "";
    }

	@Override
	public int addRide(RideSchedules rideSchedule) {
		// TODO Auto-generated method stub
		return 0;
	}
}
